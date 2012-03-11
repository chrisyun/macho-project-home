CREATE SEQUENCE nwavedm_global_sequence
INCREMENT BY 10
START WITH 10000
NOMAXVALUE
NOCYCLE
;

select nwavedm_global_sequence.nextval from dual;

CREATE SEQUENCE nwavedm_log_sequence
INCREMENT BY 1
START WITH 10
NOMAXVALUE
NOCYCLE
;

select nwavedm_log_sequence.nextval from dual;
