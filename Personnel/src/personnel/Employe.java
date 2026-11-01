package personnel;

import java.io.Serializable;
import java.time.LocalDate;

public class Employe implements Serializable, Comparable<Employe>
{
	private static final long serialVersionUID = 4795721718037994734L;
	private String nom, prenom, password, mail;
	private Ligue ligue;
	private GestionPersonnel gestionPersonnel;
	private LocalDate dateArrivee, dateDepart;

	Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom,
			String mail, String password, LocalDate dateArrivee, LocalDate dateDepart)
	{
		verifierDates(dateArrivee, dateDepart);
		this.gestionPersonnel = gestionPersonnel;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.mail = mail;
		this.ligue = ligue;
		this.dateArrivee = dateArrivee;
		this.dateDepart = dateDepart;
	}

	public boolean estAdmin(Ligue ligue)
	{
		return ligue.getAdministrateur() == this;
	}

	public boolean estRoot()
	{
		return gestionPersonnel.getRoot() == this;
	}

	public String getNom()
	{
		return nom;
	}

	public void setNom(String nom)
	{
		this.nom = nom;
	}

	public String getPrenom()
	{
		return prenom;
	}

	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}

	public String getMail()
	{
		return mail;
	}

	public void setMail(String mail)
	{
		this.mail = mail;
	}

	public boolean checkPassword(String password)
	{
		return this.password.equals(password);
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Ligue getLigue()
	{
		return ligue;
	}

	public void remove()
	{
		Employe root = gestionPersonnel.getRoot();
		if (this != root)
		{
			if (estAdmin(getLigue()))
				getLigue().setAdministrateur(root);
			getLigue().remove(this);
		}
		else
			throw new ImpossibleDeSupprimerRoot();
	}

	@Override
	public int compareTo(Employe autre)
	{
		int cmp = getNom().compareTo(autre.getNom());
		if (cmp != 0)
			return cmp;
		return getPrenom().compareTo(autre.getPrenom());
	}

	@Override
	public String toString()
	{
		String res = nom + " " + prenom + " " + mail + " (";
		if (estRoot())
			res += "super-utilisateur";
		else
			res += ligue.toString();
		return res + ")";
	}

	// ===== Ajouts étape 3 : dates =====

	public LocalDate getDateArrivee()
	{
		return dateArrivee;
	}

	public LocalDate getDateDepart()
	{
		return dateDepart;
	}

	public void setDateArrivee(LocalDate dateArrivee)
	{
		verifierDates(dateArrivee, this.dateDepart);
		this.dateArrivee = dateArrivee;
	}

	public void setDateDepart(LocalDate dateDepart)
	{
		verifierDates(this.dateArrivee, dateDepart);
		this.dateDepart = dateDepart;
	}

	private static void verifierDates(LocalDate arrivee, LocalDate depart)
	{
		if (arrivee != null && depart != null && depart.isBefore(arrivee))
			throw new DateIncoherente();
	}
}
