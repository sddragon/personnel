package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import personnel.*;

class testEmploye 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();

	private Ligue nouvelleLigue() throws SauvegardeImpossible
	{
		return gestionPersonnel.addLigue("LigueEmploye-" + System.nanoTime());
	}
	

	private boolean employeEstPresent(Ligue ligue, Employe employe)
	{
		return ligue.getEmployes().stream().anyMatch(e -> e == employe);
	}

	
	@Test
	void setNomEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = nouvelleLigue();
		Employe employe = ligue.addEmploye("Durand", "Claire", "c.durand@mail.fr", "secret");
		employe.setNom("Lambert");
		assertEquals("Lambert", employe.getNom());
	}
	
	@Test
	void setPrenomEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = nouvelleLigue();
		Employe employe = ligue.addEmploye("Durand", "Claire", "c.durand@mail.fr", "secret");
		employe.setPrenom("Camille");
		assertEquals("Camille", employe.getPrenom());
	}
	
	@Test
	void setMailEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = nouvelleLigue();
		Employe employe = ligue.addEmploye("Durand", "Claire", "c.durand@mail.fr", "secret");
		employe.setMail("nouveau@mail.fr");
		assertEquals("nouveau@mail.fr", employe.getMail());
	}
	
	@Test
	void setPasswordEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = nouvelleLigue();
		Employe employe = ligue.addEmploye("Durand", "Claire", "c.durand@mail.fr", "secret");
		assertTrue(employe.checkPassword("secret"));
		employe.setPassword("neuf");
		assertTrue(employe.checkPassword("neuf"));
		assertFalse(employe.checkPassword("secret"));
	}
	
	@Test
	void setPasswordRoot()
	{
		Employe root = gestionPersonnel.getRoot();
		try
		{
			root.setPassword("autre");
			assertTrue(root.checkPassword("autre"));
			assertFalse(root.checkPassword("toor"));
		}
		finally
		{
			
			root.setPassword("toor");
		}
		assertTrue(root.checkPassword("toor"));
	}
	
	@Test
	void toStringApresModification() throws SauvegardeImpossible
	{
		Ligue ligue = nouvelleLigue();
		Employe employe = ligue.addEmploye("Durand", "Claire", "c.durand@mail.fr", "secret");
		employe.setNom("Leroy");
		employe.setPrenom("Marc");
		employe.setMail("m.leroy@mail.fr");
		assertEquals("Leroy Marc m.leroy@mail.fr (" + ligue.getNom() + ")", employe.toString());
	}
	
	
	
	@Test
	void removeEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = nouvelleLigue();
		Employe employe = ligue.addEmploye("Garnier", "Sophie", "s.garnier@mail.fr", "pw");
		assertTrue(employeEstPresent(ligue, employe));
		employe.remove();
		assertFalse(employeEstPresent(ligue, employe));
		assertEquals(0, ligue.getEmployes().size());
	}
	
	@Test
	void removeAdministrateurRendLaMainAuRoot() throws SauvegardeImpossible
	{
		Ligue ligue = nouvelleLigue();
		Employe admin = ligue.addEmploye("Chevalier", "Paul", "p.chevalier@mail.fr", "pw");
		ligue.setAdministrateur(admin);
		assertTrue(admin.estAdmin(ligue));
		admin.remove();
		assertFalse(employeEstPresent(ligue, admin));
		assertSame(gestionPersonnel.getRoot(), ligue.getAdministrateur());
		assertTrue(gestionPersonnel.getRoot().estAdmin(ligue));
	}
	
	@Test
	void removeRoot()
	{
		Employe root = gestionPersonnel.getRoot();
		assertThrows(ImpossibleDeSupprimerRoot.class, () -> root.remove());
		assertSame(root, gestionPersonnel.getRoot());
		assertTrue(root.estRoot());
	}
	
	
	@Test
	void setNomPuisRemoveEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = nouvelleLigue();
		Employe premier = ligue.addEmploye("Alpha", "A", "a@mail.fr", "pw");
		ligue.addEmploye("Bravo", "B", "b@mail.fr", "pw");
		ligue.addEmploye("Charlie", "C", "c@mail.fr", "pw");
		premier.setNom("Zulu");
		premier.remove();
		assertFalse(employeEstPresent(ligue, premier));
		assertEquals(2, ligue.getEmployes().size());
	}
}
