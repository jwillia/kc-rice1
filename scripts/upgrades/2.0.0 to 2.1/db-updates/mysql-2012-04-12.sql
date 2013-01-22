--
-- Copyright 2005-2013 The Kuali Foundation
--
-- Licensed under the Educational Community License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
-- http://www.opensource.org/licenses/ecl2.php
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- create a Kim Type wired to the documentRouterRoleTypeService permission-derived role service
INSERT INTO KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD) values ((select KIM_TYP_ID from (select (max(cast(KIM_TYP_ID as decimal)) + 1) as KIM_TYP_ID from KRIM_TYP_T where KIM_TYP_ID is not NULL and KIM_TYP_ID REGEXP '^[1-9][0-9]*$' and cast(KIM_TYP_ID as decimal) < 10000) as tmptable), uuid(), 1, 'Derived Role: Permission (Route Document)', 'documentRouterRoleTypeService', 'Y', 'KR-WKFLW')
;
-- define the Route Document derived role
INSERT INTO KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND) values ((select ROLE_ID from (select (max(cast(ROLE_ID as decimal)) + 1) as ROLE_ID from KRIM_ROLE_T where ROLE_ID is not NULL and ROLE_ID REGEXP '^[1-9][0-9]*$' and cast(ROLE_ID as decimal) < 10000) as tmptable), uuid(), 1, 'Document Router', 'KR-WKFLW', 'This role derives its members from users with the Route Document permission for a given document type.', (select KIM_TYP_ID from KRIM_TYP_T where NM = 'Derived Role: Permission (Route Document)' and NMSPC_CD = 'KR-WKFLW'), 'Y')
;
