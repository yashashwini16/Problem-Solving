-- ==========================================
-- LLD: Multi-Modal Booking System Database Schema
-- Databases: PostgreSQL (Supports Transactional Locks & Indices)
-- ==========================================

-- 1. Users / Employees Table
CREATE TABLE users (
    user_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 2. Vehicle Types (CAB, SHUTTLE, ELECTRIC_BIKE)
CREATE TYPE vehicle_type AS ENUM ('CAB', 'SHUTTLE', 'ELECTRIC_BIKE');

-- 3. Vehicle Inventory
CREATE TABLE vehicles (
    vehicle_id VARCHAR(50) PRIMARY KEY,
    license_plate VARCHAR(20) UNIQUE NOT NULL,
    type vehicle_type NOT NULL,
    capacity INT NOT NULL, -- Total seats for shuttle/cab, 1 for electric bike
    status VARCHAR(20) DEFAULT 'ACTIVE', -- ACTIVE, MAINTENANCE, INACTIVE
    current_lat DECIMAL(9, 6),
    current_lng DECIMAL(9, 6),
    last_updated TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 4. Routes (primarily for shuttles/cabs)
CREATE TABLE routes (
    route_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    start_point VARCHAR(100) NOT NULL,
    end_point VARCHAR(100) NOT NULL,
    distance_meters INT NOT NULL
);

-- 5. Schedules (representing scheduled trips, e.g., Shuttles operating at specific slots)
CREATE TABLE schedules (
    schedule_id VARCHAR(50) PRIMARY KEY,
    vehicle_id VARCHAR(50) REFERENCES vehicles(vehicle_id),
    route_id VARCHAR(50) REFERENCES routes(route_id),
    departure_time TIMESTAMP WITH TIME ZONE NOT NULL,
    arrival_time TIMESTAMP WITH TIME ZONE NOT NULL,
    available_seats INT NOT NULL, -- Initialized to vehicles.capacity
    version INT DEFAULT 0 NOT NULL -- Used for Optimistic Locking
);

-- 6. Bookings Table (Handles active bookings)
CREATE TYPE booking_status AS ENUM ('INITIATED', 'CONFIRMED', 'CANCELLED', 'EXPIRED');

CREATE TABLE bookings (
    booking_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) REFERENCES users(user_id),
    vehicle_id VARCHAR(50) REFERENCES vehicles(vehicle_id),
    schedule_id VARCHAR(50) REFERENCES schedules(schedule_id), -- Nullable for electric bikes (on-demand)
    status booking_status DEFAULT 'INITIATED',
    seat_number INT,
    booking_time TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    lock_expires_at TIMESTAMP WITH TIME ZONE NOT NULL -- Used to release temporary seat locks
);

-- Indices for performance
CREATE INDEX idx_schedules_departure ON schedules(departure_time);
CREATE INDEX idx_bookings_user ON bookings(user_id);
CREATE INDEX idx_bookings_status_expiry ON bookings(status, lock_expires_at);


-- =========================================================================
-- CONCURRENCY CONTROL STRATEGIES FOR SEAT LOCKING
-- =========================================================================

/*
STRATEGY A: Optimistic Locking (Recommended for medium contention)
------------------------------------------------------------------
1. Query the schedule details:
   SELECT schedule_id, available_seats, version FROM schedules WHERE schedule_id = 'SCH-101';
   -- Suppose it returns available_seats = 5, version = 12

2. Attempt to book a seat:
   UPDATE schedules 
   SET available_seats = available_seats - 1, version = version + 1 
   WHERE schedule_id = 'SCH-101' AND version = 12 AND available_seats > 0;
   
3. Check the affected row count in backend logic:
   - If affected_rows == 1: Success! Proceed to insert record in 'bookings' table.
   - If affected_rows == 0: Concurrency conflict or no seats left. Rollback/Retry.


STRATEGY B: Pessimistic Locking (Required for high contention, e.g. popular shuttle route booking)
------------------------------------------------------------------
1. Start transaction in DB:
   BEGIN;
   
2. Lock the row exclusively:
   SELECT available_seats FROM schedules 
   WHERE schedule_id = 'SCH-101' FOR UPDATE;
   
3. In-application validation:
   If available_seats > 0:
       UPDATE schedules SET available_seats = available_seats - 1 WHERE schedule_id = 'SCH-101';
       INSERT INTO bookings (booking_id, user_id, schedule_id, status, lock_expires_at) 
       VALUES ('B-999', 'U-123', 'SCH-101', 'INITIATED', NOW() + INTERVAL '5 Minutes');
       COMMIT; -- Locks are released on commit
   Else:
       ROLLBACK; -- Release lock
*/
