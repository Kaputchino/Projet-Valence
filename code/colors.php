<?php
    // Connexion à la base de données
    $db = new PDO('mysql:host=localhost;dbname=mydb', 'username', 'password');
    // Récupération des informations de couleur de la table "couleur"
    $query = $db->prepare("SELECT * FROM couleur");
    $query->execute();
    $colors = $query->fetchAll(PDO::FETCH_ASSOC);
    // Envoi des informations de couleur au format JSON
    echo json_encode($colors);
?>