--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements
insert into Member (member_id, name, email, phone_number) values (0, 'John Smith', 'john.smiq@Aaa.com', '21225251212')
insert into Member (member_id, name, email, phone_number) values (3, 'Hello Wolrd', 'john.smi@Aaa.com', '21255251212')
insert into Flight(Flight_id,flight_no,flight_size,current_num,date,time,company,flight_type)  values (0,'aa33','33','22','2009-01-01','23:21','fly','b333')
insert into BookInfo (id,member_id,flight_id) values (0,0,0)
