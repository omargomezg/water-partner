insert into dropdownlist (id, value, parent_id, code, list_type)
values (1, 'Tarificador', null, '', 'PROFILE'),
       (2, 'Administrador', null, '', 'PROFILE'),
       (3, 'Recaudador', null, '', 'PROFILE'),
       (4, 'Finanzas', null, '', 'PROFILE'),
       (5, 'Socio', null, '', 'PERSON_TYPE'),
       (6, 'Usuario', null, '', 'PERSON_TYPE'),
       (7, 'Casado', null, '', 'CIVIL_STATUS'),
       (8, 'Separado', null, '', 'CIVIL_STATUS'),
       (9, 'Socio', null, 'PARTNER', 'CLIENT_TYPE'),
       (10, 'Publico', null, 'PUBLIC', 'CLIENT_TYPE'),
       (11, 'Privado', null, 'PRIVATE', 'CLIENT_TYPE'),
       (12, 'Nuevo', null, 'NEW', 'WATER_METER_STATUS'),
       (13, 'Usado', null, 'USED', 'WATER_METER_STATUS'),
       (14, 'Malo', null, 'BAD', 'WATER_METER_STATUS'),
       (15, 'Curiñanco Norte', null, '', 'COORDINATES'),
       (16, 'Curiñanco Centro', null, '', 'COORDINATES'),
       (18, 'Curiñanco Sur', null, '', 'COORDINATES'),
       (19, '13', null, '13', 'WATER_METER_SIZE'),
       (20, '19', null, '19', 'WATER_METER_SIZE'),
       (21, '25', null, '25', 'WATER_METER_SIZE');
insert into users (rut, email, is_active, names, middle_name, last_name)
