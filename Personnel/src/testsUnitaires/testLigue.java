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
	@Test
	void setAdministrateurRoot() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue(nomUnique("Natation"));
		Employe employe = ligue.addEmploye("Petit", "Luc", "l.petit@mail.fr", "pw");
		ligue.setAdministrateur(employe);
		ligue.setAdministrateur(gestionPersonnel.getRoot());
		assertTrue(gestionPersonnel.getRoot().estAdmin(ligue));
		assertFalse(employe.estAdmin(ligue));
	}
	@Test
	void changerAdministrateurRevoqueAncien() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue(nomUnique("Rugby"));
		Employe a = ligue.addEmploye("Bernard", "Anna", "a.bernard@mail.fr", "pw");
		Employe b = ligue.addEmploye("Dubois", "Paul", "p.dubois@mail.fr", "pw");
		ligue.setAdministrateur(a);
		ligue.setAdministrateur(b);
		assertFalse(a.estAdmin(ligue));
		assertTrue(b.estAdmin(ligue));
		assertSame(b, ligue.getAdministrateur());
	}
	@Test
	void setAdministrateurAutreLigue() throws SauvegardeImpossible
	{
		Ligue ligue1 = gestionPersonnel.addLigue(nomUnique("Golf"));
		Ligue ligue2 = gestionPersonnel.addLigue(nomUnique("Handball"));
		Employe employeAutreLigue = ligue2.addEmploye("Moreau", "Jean", "j.moreau@mail.fr", "pw");
		assertThrows(DroitsInsuffisants.class, () -> ligue1.setAdministrateur(employeAutreLigue));
	}
    @Test
	void setAdministrateurNull() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue(nomUnique("Cyclisme"));
		assertThrows(DroitsInsuffisants.class, () -> ligue.setAdministrateur(null));
		assertSame(gestionPersonnel.getRoot(), ligue.getAdministrateur());
	}
	
	
	@Test
	void removeLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue(nomUnique("Boxe"));
		assertTrue(ligueEstPresente(ligue));
		ligue.remove();
		assertFalse(ligueEstPresente(ligue));
	}
	

	@Test
	void removeLigueSupprimeSesEmployes() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue(nomUnique("Badminton"));
		ligue.addEmploye("Roux", "Marie", "m.roux@mail.fr", "pw");
		ligue.addEmploye("Fournier", "Hugo", "h.fournier@mail.fr", "pw");
		ligue.remove();
		assertFalse(ligueEstPresente(ligue));
		assertTrue(ligue.getEmployes().isEmpty());
	}
	

	@Test
	void setNomPuisRemoveLigue() throws SauvegardeImpossible
	{
		String suffixe = "-" + System.nanoTime();
		Ligue premiere = gestionPersonnel.addLigue("0Aaa" + suffixe);
		gestionPersonnel.addLigue("0Bbb" + suffixe);
		gestionPersonnel.addLigue("0Ccc" + suffixe);
		premiere.setNom("zzz" + suffixe);
		premiere.remove();
		assertFalse(ligueEstPresente(premiere));
	}
}
 

