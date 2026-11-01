package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
	}

	@Test
	void addEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty"); 
		assertEquals(employe, ligue.getEmployes().first());
	}
	private String nomUnique(String base)
	{
		return base + "-" + System.nanoTime();
	}
	private boolean ligueEstPresente(Ligue ligue)
	{
		return gestionPersonnel.getLigues().stream().anyMatch(l -> l == ligue);
	}
	@Test
	void setNomLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue(nomUnique("Judo"));
		String nouveauNom = nomUnique("Karaté");
		ligue.setNom(nouveauNom);
		assertEquals(nouveauNom, ligue.getNom());
		assertEquals(nouveauNom, ligue.toString());
	}
	@Test
	void administrateurParDefaut() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue(nomUnique("Escrime"));
		assertSame(gestionPersonnel.getRoot(), ligue.getAdministrateur());
	}
	
	@Test
	void setAdministrateurEmployeDeLaLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue(nomUnique("Tennis"));
		Employe employe = ligue.addEmploye("Martin", "Alice", "a.martin@mail.fr", "pw");
		ligue.setAdministrateur(employe);
		assertSame(employe, ligue.getAdministrateur());
		assertTrue(employe.estAdmin(ligue));
		assertFalse(gestionPersonnel.getRoot().estAdmin(ligue));
	}
	
}
