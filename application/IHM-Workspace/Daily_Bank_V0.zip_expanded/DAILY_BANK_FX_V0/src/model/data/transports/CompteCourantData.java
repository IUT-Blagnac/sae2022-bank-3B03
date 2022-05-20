package model.data.transports;

public class CompteCourantData {

	private int idNumCompte;
	private double solde;
	private int debitAutorise;
	private String nom;
	private String prenom;
	
	public CompteCourantData(int idNumCompte, double solde, int debitAutorise, String nom, String prenom) {
		super();
		this.idNumCompte = idNumCompte;
		this.solde = solde;
		this.debitAutorise = debitAutorise;
		this.nom = nom;
		this.prenom = prenom;
	}

	public int getIdNumCompte() {
		return idNumCompte;
	}

	public void setIdNumCompte(int idNumCompte) {
		this.idNumCompte = idNumCompte;
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}

	public int getDebitAutorise() {
		return debitAutorise;
	}

	public void setDebitAutorise(int debitAutorise) {
		this.debitAutorise = debitAutorise;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	
	
}
