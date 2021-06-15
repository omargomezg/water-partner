insert into dropdownlist (id, value, parent_id, code, list_type)
values (5, 'Socio', null, '', 'PERSON_TYPE')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (6, 'Usuario', null, '', 'PERSON_TYPE')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (7, 'Casado', null, '', 'CIVIL_STATUS')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (8, 'Separado', null, '', 'CIVIL_STATUS')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (9, 'Socio', null, 'PARTNER', 'CLIENT_TYPE')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (10, 'Publico', null, 'PUBLIC', 'CLIENT_TYPE')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (11, 'Privado', null, 'PRIVATE', 'CLIENT_TYPE')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (12, 'Nuevo', null, 'NEW', 'WATER_METER_STATUS')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (13, 'Usado', null, 'USED', 'WATER_METER_STATUS')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (14, 'Malo', null, 'BAD', 'WATER_METER_STATUS')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (15, 'Curiñanco Norte', null, '', 'COORDINATES')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (16, 'Curiñanco Centro', null, '', 'COORDINATES')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (18, 'Curiñanco Sur', null, '', 'COORDINATES')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (19, '13', null, '13', 'WATER_METER_SIZE')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (20, '19', null, '19', 'WATER_METER_SIZE')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (21, '25', null, '25', 'WATER_METER_SIZE')
on conflict do nothing;

insert into company (id, fullName, shortName, identifier)
values (nextval('hibernate_sequence'), 'Comité de Agua Potable Rural Curiñanco', 'APR Curiñanco', '737419002')
on conflict do nothing;

insert into attribute (id, key, value, company_id)
values (nextval('hibernate_sequence'), 'flow_key', '5B656F4B-448C-42CB-98B5-8E7CDB96L7DA', (
    select id
    from company
    where identifier = '737419002'
    LIMIT 1
))
on conflict do nothing;

insert into attribute (id, key, value, company_id)
values (nextval('hibernate_sequence'), 'flow_token', 'DujitxSHfwH6Y99gw0adoK9tPqDHbsq2', (
    select id
    from company
    where identifier = '737419002'
    LIMIT 1
))
on conflict do nothing;

insert into attribute (id, key, value, company_id)
values (nextval('hibernate_sequence'), 'libreDte_hash', '122a8472e2a2016e552a7462ad9d61f3', (
    select id
    from company
    where identifier = '737419002'
    LIMIT 1
))
on conflict do nothing;
