# nosql
HMIN 313 - NoSQL

Support de cours sur Claroline

## Installation HBase

Télécharger HBASE :
http://apache.crihan.fr/dist/hbase/hbase-0.94.27/

### Configuration

Spécifier le repertoire de Java :
```
export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home"
```
Ajoutable au fichier conf

### Utilisation

Lancer le serveur :
```
bin/hbase_start
```

Accés au bash Perl :
```
bin/hbase shell
```

## Résumé des séances

### Séance du 16/09

- [Cours d'intro](https://github.com/Doelia/M2-nosql/raw/master/cours/HMIN313_c1_15.pdf)
- Début [TP sur HBase](https://github.com/Doelia/M2-nosql/raw/master/cours/HMIN313_Td1.pdf) :
  - Installation
  - Requêtes de base en shell (put, get...)
  - Requêtes en Java : Put, Scanner, Filter...

### Séance du 23/09
- Cours :
  - Fin du Cours d'intro
  - [Cours 2](https://github.com/Doelia/M2-nosql/raw/master/cours/HMIN313_c2_15.pdf)
- Continuité du TP

### Séance du 30/09
- Cours
    - Cours sur les [bases de données orientée graphes](https://github.com/Doelia/M2-nosql/raw/master/cours/HMIN313_c3_15.pdf)
    - Zoom sur Neo4J
        - [Cheat Sheet](https://github.com/Doelia/M2-nosql/raw/master/cours/Neo4j_CheatSheet_v3.pdf)
- [TP Neo4J](https://github.com/Doelia/M2-nosql/raw/master/cours/HMIN313_Td2.pdf)
    - API Java
    - Serveur qui sert à rien ?
