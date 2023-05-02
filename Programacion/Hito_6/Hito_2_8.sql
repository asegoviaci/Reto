--Procedimiento
CREATE OR REPLACE PROCEDURE uspdatoscliente(a_DNI IN clientes.DNI%TYPE)IS
a_nombre clientes.NOMBRE%TYPE;
a_apellido clientes.apellido%TYPE;
a_correo clientes.correo%TYPE;
BEGIN
SELECT nombre, apellido, correo INTO a_nombre, a_apellido, a_correo
FROM CLIENTES
WHERE a_DNI = dni;
DBMS_output.put_line('DNI:'|| a_DNI || ' Nombre: '|| a_nombre || ' Apellido: '|| a_apellido|| ' Correo: ' || a_correo);
END;
--Funcion
CREATE OR REPLACE FUNCTION usfdatospasaje(a_DNI IN pasaje.DNI%TYPE,
											a_clase IN  pasaje.CLASE%TYPE)
RETURN INTEGER
IS
a_cant INTEGER;
BEGIN
SELECT count(*) INTO a_cant
FROM pasaje
WHERE a_DNI = DNI AND clase = a_clase;
RETURN a_cant;
END;
--Bloque anonimo
DECLARE
	CURSOR k1 IS
	SELECT DNI
	FROM CLIENTES;
	a_kur k1%rowtype;
	a_cant int;
	a_cant_total int;
	a_clase pasaje.clase%TYPE;
	a_cant_clientes int;
	a_coste_total float;
	a_cant_pasaje float;
BEGIN
	a_cant_total := 0;
	a_clase := 'a';
	FOR a_kur IN k1 LOOP
		uspdatoscliente(A_KUR.dni);
		FOR a_kur2 IN (SELECT clase FROM pasaje WHERE DNI=A_KUR.DNI) LOOP 
		IF a_clase != a_kur2.clase then
		DBMS_output.put_line(A_KUR2.clase||': ' ||usfdatospasaje(a_kur.DNI, a_kur2.clase));
		a_cant_total:= a_cant_total + usfdatospasaje(a_kur.DNI, a_kur2.clase);
		a_clase:= a_kur2.clase;
		END IF;
		END LOOP;
		DBMS_output.put_line('--------------------------------');
	END LOOP;
	SELECT COUNT(*), SUM(precio) INTO a_cant_pasaje, a_coste_total FROM pasaje;
	SELECT count(*) INTO a_cant_clientes FROM CLIENTES;
	a_cant_pasaje:= a_cant_pasaje/a_cant_clientes;
	a_coste_total:= a_coste_total/a_cant_clientes;
	DBMS_output.put_line('Promedio de pasajes: ' || round(A_CANT_PASAJE, 2) || ' Promedio de coste: ' || round(a_coste_total, 2));
END;