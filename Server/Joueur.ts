export class Joueur {
  prenom: string;
  nom: string;
  age: number;
  rang: number;
  pays: string;
  constructor(prenom, nom, age, rang, pays) {
    this.prenom = prenom;
    this.nom = nom;
    this.age = age;
    this.rang = rang;
    this.pays = pays;
  }
}
