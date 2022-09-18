import { Joueur } from 'Joueur';
import { Partie } from 'Partie';

const modificateurVitesse = Math.max(Number(process.argv[2]), 1);

export const listePartie = [];

listePartie.push(
  new Partie(
    listePartie.length,
    new Joueur('Albert', 'Ramos', 28, 56, 'Espagne'),
    new Joueur('Milos', 'Raonic', 28, 16, 'Canada'),
    '1',
    'Hale',
    '12h30',
    0,
  ),
);
listePartie.push(
  new Partie(
    listePartie.length,
    new Joueur('Andy', 'Murray', 28, 132, 'Angleterre'),
    new Joueur('Andy', 'Roddick', 36, 1000, 'États-Unis'),
    '2',
    'Hale',
    '13h00',
    30,
  ),
);

export const demarrer = function () {
  let tick = 0;
  setInterval(function () {
    for (const partie in listePartie) {
      if (listePartie[partie].tick_debut === tick) {
        demarrerPartie(listePartie[partie]);
      }
    }

    tick += 1;
  }, Math.floor(1000 / modificateurVitesse));
};

export function demarrerPartie(partie) {
  const timer = setInterval(function () {
    partie.jouerTour();
    if (partie.estTerminee()) {
      clearInterval(timer);
    }
  }, Math.floor(1000 / modificateurVitesse));
}
