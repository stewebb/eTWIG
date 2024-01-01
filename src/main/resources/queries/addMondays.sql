CREATE OR REPLACE FUNCTION insert_mondays_between(start_date DATE, end_date DATE, semester_value VARCHAR) RETURNS VOID AS $$
DECLARE
    current_date_ DATE;
BEGIN
    -- Find the first Monday on or after the start date
    current_date_ := start_date + CAST(((7 - EXTRACT(DOW FROM start_date) + 1) % 7) AS INTEGER) * INTERVAL '1 day';

    -- Loop through the dates and insert Mondays
    WHILE current_date_ BETWEEN start_date AND end_date LOOP
        INSERT INTO public.week (monday, semester) VALUES (current_date_, semester_value);
        current_date_ := current_date_ + INTERVAL '7 days'; -- Move to next Monday
    END LOOP;
END;
$$ LANGUAGE plpgsql;

SELECT insert_mondays_between('2024-02-01', '2024-06-30', '2024 Semester 1');