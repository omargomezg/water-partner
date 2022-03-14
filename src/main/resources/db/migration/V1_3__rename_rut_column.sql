DO $$ BEGIN
    IF EXISTS (SELECT column_name FROM information_schema.columns where table_schema = 'public'
                                                                    and table_name = 'client' and column_name = 'rut' ) THEN
        ALTER TABLE client RENAME COLUMN rut to dni;
    ELSE
        RAISE NOTICE 'Columna dni en table cliente ya existe';
    END IF;
END $$;

DO $$ BEGIN
    IF EXISTS (SELECT column_name FROM information_schema.columns where table_schema = 'public'
                                                                    and table_name = 'users' and column_name = 'rut' ) THEN
        ALTER TABLE users RENAME COLUMN rut to dni;
    ELSE
        RAISE NOTICE 'Columna dni en tabla users ya existe';
    END IF;
END $$;

ALTER TABLE client
    ADD COLUMN if not exists nationality varchar(15) not null default 'CHILEAN';

ALTER TABLE client
    ADD COLUMN if not exists type_of_dni varchar(15) not null default 'CHILEAN';

ALTER TABLE users
    ADD COLUMN if not exists nationality varchar(15) not null default 'CHILEAN';

DO $$ BEGIN
    IF EXISTS (SELECT column_name FROM information_schema.columns where table_schema = 'public'
                                                                    and table_name = 'watermeter' and column_name = 'client_rut' ) THEN
        ALTER TABLE watermeter RENAME COLUMN client_rut to client_dni;
    ELSE
        RAISE NOTICE 'Columna client_dni en tabla watermeter ya existe';
    END IF;
END $$;

DO $$ BEGIN
    IF EXISTS (SELECT column_name FROM information_schema.columns where table_schema = 'public'
                                                                    and table_name = 'invoice' and column_name = 'client_rut' ) THEN
        ALTER TABLE invoice RENAME COLUMN client_rut to client_dni;
    ELSE
        RAISE NOTICE 'Columna client_dni en tabla invoice ya existe';
    END IF;
END $$;

DO $$ BEGIN
    IF EXISTS (SELECT column_name FROM information_schema.columns where table_schema = 'public'
                                                                    and table_name = 'bill' and column_name = 'client_rut' ) THEN
        ALTER TABLE bill RENAME COLUMN client_rut to client_dni;
    ELSE
        RAISE NOTICE 'Columna client_dni en tabla bill ya existe';
    END IF;
END $$;
