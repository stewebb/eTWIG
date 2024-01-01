CREATE OR REPLACE FUNCTION insert_mondays(year int)
RETURNS void AS $$
DECLARE
    current_date_ date;
	
BEGIN 
	current_date_ := make_date(year, 1, 1);

    -- Loop through the days of the year
    LOOP
        EXIT WHEN EXTRACT(YEAR FROM current_date_) > year;

        -- Check if the day is Monday (1 = Monday, 2 = Tuesday, ..., 7 = Sunday)
        IF EXTRACT(ISODOW FROM current_date_) = 1 THEN
            INSERT INTO public.week (id, name, semester, monday)
            VALUES (nextval('week_id_seq'), NULL, NULL, current_date_);
        END IF;

        -- Move to the next day
        current_date_ := current_date_ + INTERVAL '1 day';
    END LOOP;
END;

$$ LANGUAGE plpgsql;

SELECT insert_mondays(2024);
--
-- PostgreSQL database dump
--

-- Dumped from database version 14.10 (Ubuntu 14.10-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.10

-- Started on 2023-12-28 04:23:49 UTC

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 210 (class 1259 OID 16479)
-- Name: event; Type: TABLE; Schema: public; Owner: etwig.test
--

CREATE TABLE public.event (
    id integer NOT NULL,
    is_recurring boolean DEFAULT false NOT NULL,
    organizer integer NOT NULL,
    portfolio integer NOT NULL
);


ALTER TABLE public.event OWNER TO "etwig.test";

--
-- TOC entry 211 (class 1259 OID 16483)
-- Name: etwig_events_EventID_seq; Type: SEQUENCE; Schema: public; Owner: etwig.test
--

ALTER TABLE public.event ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public."etwig_events_EventID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 209 (class 1259 OID 16397)
-- Name: etwig_events_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.etwig_events_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.etwig_events_seq OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16484)
-- Name: event_recurring; Type: TABLE; Schema: public; Owner: etwig.test
--

CREATE TABLE public.event_recurring (
    id integer NOT NULL,
    name character varying(31) NOT NULL,
    description character varying(65535),
    location character varying(63),
    frequency character varying(63) NOT NULL,
    duration integer NOT NULL,
    available_from timestamp(6) without time zone,
    available_to timestamp(6) without time zone
);


ALTER TABLE public.event_recurring OWNER TO "etwig.test";

--
-- TOC entry 213 (class 1259 OID 16489)
-- Name: event_single_time; Type: TABLE; Schema: public; Owner: etwig.test
--

CREATE TABLE public.event_single_time (
    id integer NOT NULL,
    name character varying(31) NOT NULL,
    description character varying(65535),
    location character varying(63),
    start_datetime timestamp(6) without time zone NOT NULL,
    duration integer NOT NULL,
    override_recurring_event integer,
    unit "char"
);


ALTER TABLE public.event_single_time OWNER TO "etwig.test";

--
-- TOC entry 214 (class 1259 OID 16494)
-- Name: users; Type: TABLE; Schema: public; Owner: etwig.test
--

CREATE TABLE public.users (
    id integer NOT NULL,
    first_name character varying(31) NOT NULL,
    middle_name character varying(31),
    last_name character varying(31) NOT NULL,
    email character varying(63) NOT NULL,
    password character varying(63) NOT NULL,
    last_login timestamp(6) without time zone DEFAULT now() NOT NULL,
    permission integer NOT NULL
);


ALTER TABLE public.users OWNER TO "etwig.test";

--
-- TOC entry 215 (class 1259 OID 16498)
-- Name: leader_id_seq; Type: SEQUENCE; Schema: public; Owner: etwig.test
--

ALTER TABLE public.users ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.leader_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 216 (class 1259 OID 16499)
-- Name: options; Type: TABLE; Schema: public; Owner: etwig.test
--

CREATE TABLE public.options (
    id integer NOT NULL,
    name character varying(31) NOT NULL,
    color character varying(7) NOT NULL,
    belongs_to integer NOT NULL
);


ALTER TABLE public.options OWNER TO "etwig.test";

--
-- TOC entry 217 (class 1259 OID 16502)
-- Name: options_id_seq; Type: SEQUENCE; Schema: public; Owner: etwig.test
--

ALTER TABLE public.options ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.options_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 218 (class 1259 OID 16503)
-- Name: permission; Type: TABLE; Schema: public; Owner: etwig.test
--

CREATE TABLE public.permission (
    id integer NOT NULL,
    name character varying(31) NOT NULL
);


ALTER TABLE public.permission OWNER TO "etwig.test";

--
-- TOC entry 219 (class 1259 OID 16506)
-- Name: portfolio; Type: TABLE; Schema: public; Owner: etwig.test
--

CREATE TABLE public.portfolio (
    id integer NOT NULL,
    name character varying(31) NOT NULL,
    color character varying(15) DEFAULT 0 NOT NULL,
    abbreviation character varying(15),
    icon character varying(31),
    is_seperated_calendar boolean NOT NULL,
    parent integer
);


ALTER TABLE public.portfolio OWNER TO "etwig.test";

--
-- TOC entry 220 (class 1259 OID 16510)
-- Name: portfolio_id_seq; Type: SEQUENCE; Schema: public; Owner: etwig.test
--

ALTER TABLE public.portfolio ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.portfolio_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 221 (class 1259 OID 16511)
-- Name: user_role; Type: TABLE; Schema: public; Owner: etwig.test
--

CREATE TABLE public.user_role (
    user_id integer NOT NULL,
    portfolio_id integer NOT NULL,
    "position" character varying(63) NOT NULL
);


ALTER TABLE public.user_role OWNER TO "etwig.test";

--
-- TOC entry 3372 (class 0 OID 16479)
-- Dependencies: 210
-- Data for Name: event; Type: TABLE DATA; Schema: public; Owner: etwig.test
--



--
-- TOC entry 3374 (class 0 OID 16484)
-- Dependencies: 212
-- Data for Name: event_recurring; Type: TABLE DATA; Schema: public; Owner: etwig.test
--



--
-- TOC entry 3375 (class 0 OID 16489)
-- Dependencies: 213
-- Data for Name: event_single_time; Type: TABLE DATA; Schema: public; Owner: etwig.test
--



--
-- TOC entry 3378 (class 0 OID 16499)
-- Dependencies: 216
-- Data for Name: options; Type: TABLE DATA; Schema: public; Owner: etwig.test
--



--
-- TOC entry 3380 (class 0 OID 16503)
-- Dependencies: 218
-- Data for Name: permission; Type: TABLE DATA; Schema: public; Owner: etwig.test
--

INSERT INTO public.permission VALUES (1, 'Administrator');
INSERT INTO public.permission VALUES (2, 'Content Manager');
INSERT INTO public.permission VALUES (3, 'Event Manager');


--
-- TOC entry 3381 (class 0 OID 16506)
-- Dependencies: 219
-- Data for Name: portfolio; Type: TABLE DATA; Schema: public; Owner: etwig.test
--

INSERT INTO public.portfolio VALUES (1, 'Arts', 'FF0000', 'AT', 'palette', false, NULL);
INSERT INTO public.portfolio VALUES (2, 'Social', '00FF00', NULL, 'person', false, NULL);
INSERT INTO public.portfolio VALUES (3, 'Sports', '0000FF', NULL, 'baseball', true, NULL);
INSERT INTO public.portfolio VALUES (4, 'Dummy1', 'FFFF00', NULL, NULL, false, NULL);
INSERT INTO public.portfolio VALUES (5, 'Dummy2', '00FFFF', 'D2', NULL, false, NULL);
INSERT INTO public.portfolio VALUES (6, 'Dummy3', 'FF00FF', NULL, NULL, false, NULL);


--
-- TOC entry 3383 (class 0 OID 16511)
-- Dependencies: 221
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: etwig.test
--

INSERT INTO public.user_role VALUES (1, 1, 'Arts Rep');
INSERT INTO public.user_role VALUES (2, 2, 'Social Rep');
INSERT INTO public.user_role VALUES (3, 3, 'Sports Rep');


--
-- TOC entry 3376 (class 0 OID 16494)
-- Dependencies: 214
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: etwig.test
--

INSERT INTO public.users VALUES (1, 'Test', 'Arts', 'Rep', 'test.arts.rep@test.com', '$2y$10$2xHfEHfGSMKW0MAcZo2Ksu6/P8Utq9.qvxbbDPlRCHRlbrTAHqP5q', '1970-01-01 00:00:00', 3);
INSERT INTO public.users VALUES (2, 'Test', 'Social', 'Rep', 'test.social.rep@test.com', '$2y$10$2xHfEHfGSMKW0MAcZo2Ksu6/P8Utq9.qvxbbDPlRCHRlbrTAHqP5q', '1970-01-01 00:00:00', 3);
INSERT INTO public.users VALUES (3, 'Test', 'Sports', 'Repb', 'test.sports.rep@test.com', '$2y$10$2xHfEHfGSMKW0MAcZo2Ksu6/P8Utq9.qvxbbDPlRCHRlbrTAHqP5q', '1970-01-01 00:00:00', 3);
INSERT INTO public.users VALUES (4, 'Another', 'Arts', 'Rep', 'another.arts.rep@test.com', '$2y$10$2xHfEHfGSMKW0MAcZo2Ksu6/P8Utq9.qvxbbDPlRCHRlbrTAHqP5q', '1970-01-01 00:00:00', 3);


--
-- TOC entry 3389 (class 0 OID 0)
-- Dependencies: 211
-- Name: etwig_events_EventID_seq; Type: SEQUENCE SET; Schema: public; Owner: etwig.test
--

SELECT pg_catalog.setval('public."etwig_events_EventID_seq"', 22, true);


--
-- TOC entry 3390 (class 0 OID 0)
-- Dependencies: 209
-- Name: etwig_events_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.etwig_events_seq', 1, false);


--
-- TOC entry 3391 (class 0 OID 0)
-- Dependencies: 215
-- Name: leader_id_seq; Type: SEQUENCE SET; Schema: public; Owner: etwig.test
--

SELECT pg_catalog.setval('public.leader_id_seq', 1, false);


--
-- TOC entry 3392 (class 0 OID 0)
-- Dependencies: 217
-- Name: options_id_seq; Type: SEQUENCE SET; Schema: public; Owner: etwig.test
--

SELECT pg_catalog.setval('public.options_id_seq', 1, false);


--
-- TOC entry 3393 (class 0 OID 0)
-- Dependencies: 220
-- Name: portfolio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: etwig.test
--

SELECT pg_catalog.setval('public.portfolio_id_seq', 1, false);


--
-- TOC entry 3207 (class 2606 OID 16515)
-- Name: event etwig_events_pkey; Type: CONSTRAINT; Schema: public; Owner: etwig.test
--

ALTER TABLE ONLY public.event
    ADD CONSTRAINT etwig_events_pkey PRIMARY KEY (id);


--
-- TOC entry 3222 (class 2606 OID 16517)
-- Name: portfolio etwig_portfolio_pkey; Type: CONSTRAINT; Schema: public; Owner: etwig.test
--

ALTER TABLE ONLY public.portfolio
    ADD CONSTRAINT etwig_portfolio_pkey PRIMARY KEY (id);


--
-- TOC entry 3212 (class 2606 OID 16519)
-- Name: event_single_time event_single_time_pkey; Type: CONSTRAINT; Schema: public; Owner: etwig.test
--

ALTER TABLE ONLY public.event_single_time
    ADD CONSTRAINT event_single_time_pkey PRIMARY KEY (id);


--
-- TOC entry 3209 (class 2606 OID 16521)
-- Name: event_recurring events_recurring_pkey; Type: CONSTRAINT; Schema: public; Owner: etwig.test
--

ALTER TABLE ONLY public.event_recurring
    ADD CONSTRAINT events_recurring_pkey PRIMARY KEY (id);


--
-- TOC entry 3218 (class 2606 OID 16523)
-- Name: options options_pkey; Type: CONSTRAINT; Schema: public; Owner: etwig.test
--

ALTER TABLE ONLY public.options
    ADD CONSTRAINT options_pkey PRIMARY KEY (id);


--
-- TOC entry 3220 (class 2606 OID 16525)
-- Name: permission permission_pkey; Type: CONSTRAINT; Schema: public; Owner: etwig.test
--

ALTER TABLE ONLY public.permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (id);


--
-- TOC entry 3226 (class 2606 OID 16527)
-- Name: user_role user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: etwig.test
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (user_id, portfolio_id);


--
-- TOC entry 3216 (class 2606 OID 16529)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: etwig.test
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3213 (class 1259 OID 16530)
-- Name: fki_event_id_fkey; Type: INDEX; Schema: public; Owner: etwig.test
--

CREATE INDEX fki_event_id_fkey ON public.event_single_time USING btree (id);


--
-- TOC entry 3210 (class 1259 OID 16531)
-- Name: fki_i; Type: INDEX; Schema: public; Owner: etwig.test
--

CREATE INDEX fki_i ON public.event_recurring USING btree (id);


--
-- TOC entry 3223 (class 1259 OID 16532)
-- Name: fki_portfolio_id_fkey; Type: INDEX; Schema: public; Owner: etwig.test
--

CREATE INDEX fki_portfolio_id_fkey ON public.user_role USING btree (portfolio_id);


--
-- TOC entry 3224 (class 1259 OID 16533)
-- Name: fki_user_id_fkey; Type: INDEX; Schema: public; Owner: etwig.test
--

CREATE INDEX fki_user_id_fkey ON public.user_role USING btree (user_id);


--
-- TOC entry 3214 (class 1259 OID 16534)
-- Name: user_group_fkey; Type: INDEX; Schema: public; Owner: etwig.test
--

CREATE INDEX user_group_fkey ON public.users USING btree (permission);


--
-- TOC entry 3228 (class 2606 OID 16535)
-- Name: event_single_time event_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: etwig.test
--

ALTER TABLE ONLY public.event_single_time
    ADD CONSTRAINT event_id_fkey FOREIGN KEY (id) REFERENCES public.event(id) NOT VALID;


--
-- TOC entry 3227 (class 2606 OID 16540)
-- Name: event_recurring event_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: etwig.test
--

ALTER TABLE ONLY public.event_recurring
    ADD CONSTRAINT event_id_fkey FOREIGN KEY (id) REFERENCES public.event(id) NOT VALID;


--
-- TOC entry 3229 (class 2606 OID 16545)
-- Name: users permission_fkey; Type: FK CONSTRAINT; Schema: public; Owner: etwig.test
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT permission_fkey FOREIGN KEY (permission) REFERENCES public.permission(id) NOT VALID;


--
-- TOC entry 3230 (class 2606 OID 16550)
-- Name: user_role portfolio_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: etwig.test
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT portfolio_id_fkey FOREIGN KEY (portfolio_id) REFERENCES public.portfolio(id) NOT VALID;


--
-- TOC entry 3231 (class 2606 OID 16555)
-- Name: user_role user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: etwig.test
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) NOT VALID;


-- Completed on 2023-12-28 04:23:49 UTC

--
-- PostgreSQL database dump complete
--
