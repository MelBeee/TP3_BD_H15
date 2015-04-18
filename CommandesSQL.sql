  -- #1 Ajouter/Modifier/Supprimer Adherent 

insert into adherent (nom,prenom,adresse,telephone) values ('','','','');

update adherent set nom = '', prenom = '', adresse = '', telephone = '' where numadherent = 0;

delete from adherent where numadherent = 0; 

-- #2 Consulter la liste des livres par genre. 

select l.numlivre, l.titre, l.auteur, l.dateparution, l.maisonedition, genre from livre l
inner join genre on genre.codegenre = l.codegenre
where g.GENRE = '';

-- #3 Ajouter un pret

insert into emprunt values (0, 0, curdate(), curdate() + 30);

-- #4 Consulter la liste des livres en cours de prêts 

select l.titre, g.genre, e.dateemprunt, e.dateretour, ad.nom, ad.prenom
from EMPRUNT e
inner join adherent ad on ad.NUMADHERENT = e.NUMADHERENT
inner join exemplaire ex on ex.NUMEXEMPLAIRE = e.NUMEXEMPLAIRE
inner join livre l on l.NUMLIVRE = ex.NUMLIVRE
inner join genre g on g.CODEGENRE = l.CODEGENRE;

-- #5 Recherche d'un livre par auteur ou par titre

select l.numlivre, l.titre, l.auteur, l.dateparution, l.maisonedition, genre from livre l
inner join genre on genre.codegenre = l.codegenre
where AUTEUR = '';

select l.numlivre, l.titre, l.auteur, l.dateparution, l.maisonedition, genre from livre l
inner join genre on genre.codegenre = l.codegenre
where titre like '%';


-- #6 

select l.*, count(ex.numlivre) as NbreEmprunt from livre l
inner join exemplaire ex on ex.NUMLIVRE = l.NUMLIVRE
order by NbreEmprunt;

