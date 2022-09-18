import { Injectable } from '@nestjs/common';
import { Partie } from 'Partie';
import { demarrer, listePartie } from 'Generateur';

@Injectable()
export class PartieService {
  constructor() {
    demarrer()
  }

  getAllParties(): Partie[] {
    return listePartie
  }

  getPartie(id: number): Partie[] {
    return listePartie.find(x => x.id_partie == id)
  }
}
