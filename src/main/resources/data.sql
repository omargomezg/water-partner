insert into dropdownlist (id, value, parent_id, code, list_type)
values (1, 'Tarificador', null, '', 'PROFILE')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (2, 'Administrador', null, '', 'PROFILE')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (3, 'Recaudador', null, '', 'PROFILE')
on conflict do nothing;

insert into dropdownlist (id, value, parent_id, code, list_type)
values (4, 'Finanzas', null, '', 'PROFILE')
on conflict do nothing;

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
