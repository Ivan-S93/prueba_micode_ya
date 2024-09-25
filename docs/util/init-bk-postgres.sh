## crear Directorios
> mkdir /opt/bk/pg/encomiendasspbd/logs
> mkdir /opt/bk/pg/encomiendasspbd/backups

## Crear archivo credencial
> vi /opt/bk/pg/encomiendasspbd/.pgpasss
```
localhost:5432:encomiendasspbd:encomiendasspuser:password
```
## Agregar permiso correcto al archivo credencial
> chmod 0600 /opt/bk/pg/encomiendasspbd/.pgpasss

## Crear archivo con script
> vi /opt/bk/pg/encomiendasspbd/ini-bk-encomiendasspbd-postgres.sh
```
#!/bin/bash

######################################################
#						                             #	
# Script para backup de base de datos postgres       #
# y subir a box.com									 #
#                                                    #
######################################################

fun=$1
function bstart {
        # Completar
        backupdir="/opt/bk/pg/encomiendasspbd"
        backufilename="encomiendasspbd-postgres_$(date +'%Y%m%d%H%M%S')"
        pgUser="encomiendasspuser"
        pgBd="encomiendasspbd"
		boxFolderId="123456678"
		jarName="jar-box-com-client-7788"

        # No tocar
        logfile="$backupdir/logs/$backufilename.log"
        echo "Se inicia el proceso de backup $(date +'%Y-%m-%d %H:%M:%S')" > $logfile
        echo "backufilename= $backufilename" >> $logfile
		echo "backupdir= $backupdir" >> $logfile
        PGPASSFILE=$backupdir/.pgpasss pg_dump -U $pgUser -w -Fc $pgBd > $backupdir/backups/$backufilename.backup
        echo "Finalizo el backup $(date +'%Y-%m-%d %H:%M:%S')" >> $logfile
        echo "Se inicia el proceso de subir a box $(date +'%Y-%m-%d %H:%M:%S')" >> $logfile
        comandoIniciarServicio="/opt/jdk-17.0.4.1/bin/java -jar -Dparam.upload.file=\"$backupdir/backups/$backufilename.backup\" -Dparam.upload.folderId=$boxFolderId /opt/springboot/apps/box-com-client/$jarName.jar --server.port=7788 --spring.config.location=/opt/springboot/config/box-com-client/application.properties > \"/opt/springboot/data/box-com-client/logs/$jarName_$(date +'%Y-%m-%d-%H.%M.%S').log\" 2>&1 &"
        echo "script: ${comandoIniciarServicio}" >> $logfile
        eval $comandoIniciarServicio
        echo "Finalizo el proceso de subir a box $(date +'%Y-%m-%d %H:%M:%S')" >> $logfile
}

if [ -z "$funi" ]; then
    fun="bstart"
fi

if [ $fun = "bstart" ]; then
 bstart
fi


```


## Agregar permiso de ejecucion
> chmod +x /opt/bk/pg/encomiendasspbd/ini-bk-encomiendasspbd-postgres.sh

## Ejecutar
/opt/bk/pg/encomiendasspbd/ini-bk-encomiendasspbd-postgres.sh

## Ajuste de permisos
Cambiar owner a postgres de lo necesario
> chown -R postgres:postgres /opt/bk/pg/encomiendasspbd/
> chown -R postgres:postgres /opt/springboot/data/box-com-client/

## Tarea programada
- Editar crontab del user postgres
> crontab -u postgres -e
- contenido, ejecuta 3 veces al dia, comentar con # si se desea quitar
```
0 2 * * * /opt/bk/pg/encomiendasspbd/ini-bk-encomiendasspbd-postgres.sh 2>&1
```
- Listar crontabs del user postgres
> crontab -u postgres -l

