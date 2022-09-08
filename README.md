# Openclassrooms - Projet 5 : Création d'une API avec Spring Boot

## Contexte
<i>Safety Net</i> souhaite développer une application de type API afin de permettre de faciliter la gestion des sinistres (incendies, inondations...) avec les services d'urgences. Elle donne la liste des habitants, avec leurs informations personnelles (âge, domiciliation, dossier médical...), d'une ville. Celle-ci est découpée en quartiers et répartie sur plusieurs casernes de service d'interventions.

Les données à consommer sont issues d'un fichier JSON

## Processus
### Etude des end-points de l'API fournies
- <i>http://localhost:8080/firestation?stationNumber=<station_number></i> : doit retourner une liste des personnes couvertes par la caserne de pompiers correspondante.
- <i>http://localhost:8080/childAlert?address=<address></i> : doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
- <i>http://localhost:8080/phoneAlert?firestation=<firestation_number></i> : doit retourner une liste des numéros de téléphone des résidents desservis par la caserne de pompiers.
- <i>http://localhost:8080/fire?address=<address></i> : doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
de pompiers la desservant. La liste doit inclure le nom, le numéro de téléphone, l'âge et les antécédents médicaux de chaque personne.
- <i>http://localhost:8080/flood/stations?stations=<a list of station_numbers></i> : doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper les personnes par adresse. Elle doit aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et faire figurer leurs antécédents médicaux à côté de chaque nom.
- <i>http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName></i> : doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux de chaque habitant. Si plusieurs personnes portent le même nom, elles doivent toutes apparaître.
- <i>http://localhost:8080/communityEmail?city=<city></i> : doit retourner les adresses mail de tous les habitants de la ville.


### Implémentation du code
- Conversion des données Json en POJO
- Construction d'une architecture MVC
- Création du CRUD : personne, dossier médical et caserne
- Rédaction et validation des tests unitaires et d'implémentation avec JUnit, AssertJ, Mockito et Postman
- Implémentation de chaque end-point 
- Création des Data Transfert Objects
- Rédaction et validation avec tests unitaires et d'implémentation
- Validation de la couverture du code avec JaCoCo et SureFire
- Génération des rapports
- Rédaction de la documentation avec JavaDoc
