package ihm.console;

import dao.JpaUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.modele.Genre;
import metier.modele.Medium;
import metier.modele.Prediction;
import metier.service.Service;

public class Main {

    public static void main(String[] args) {
        //JpaUtil.desactiverLog();
        JpaUtil.initialiserFabriqueContextePersistance();
        try {
            testerInscrireClients();
            testerConsulterListe();
            testerStatistiques();
            testerConsulterListe();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JpaUtil.libererFabriqueContextePersistance();
    }

    private static void testerInscrireClients() throws Exception {
        Service service = new Service();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date dateNaissance = simpleDateFormat.parse("10/12/1995");
        printlnConsoleIHM("Inscription Client C1");

        Client c1 = new Client("Hugo", "Victor", "victor.hugo@gmail.com", "esmeralda", "place bellecour", dateNaissance, "+330669099781", Genre.M);
        Boolean resultat1 = service.inscrireClient(c1);
        printlnConsoleIHM(resultat1 + " -> Inscription client C1 " + c1);

        Employe e1 = new Employe("Hugo", "Marin", "victor.marin@gmail.com", "esmeralda", "+330669099781", "+33645263258", Genre.M);
        Boolean resultat2 = service.inscrireEmploye(e1);
        printlnConsoleIHM(resultat2 + " -> Inscription employe E1 " + e1);

        Medium m1 = new Medium("medium", "astronomique", Genre.M);
        Boolean resultat3 = service.inscrireMedium(m1);
        printlnConsoleIHM(resultat3 + " -> Inscription Medium M1 " + m1);

        Client c2 = service.authentifierClient("victor.hugo@gmail.com", "esmeralda");
        printlnConsoleIHM("Authentification Client C2 : " + c2.getNom() + " " + c2.getPrenom() + " ");

        boolean resultat4 = service.creerConsultation(c1, m1);
        Employe e3 = service.authentifierEmploye("victor.marin@gmail.com", "esmeralda");
        printlnConsoleIHM("Authentification Employe E2 : " + e3.getNom() + " " + e3.getPrenom() + " ");
        printlnConsoleIHM(resultat4 + " -> Inscription Consultation cons1 " + e3.getConsultations() + "client: " + c1.getConsultations());

        Consultation cons2 = e3.getConsultationCourante();
        boolean resultat5 = service.accepterConsultation(cons2);
        printlnConsoleIHM(resultat5 + " -> Consultation accepte " + cons2);

        Prediction prediction = service.genererPrediction(c1, 2, 3, 4);
        printlnConsoleIHM("Prediction : " + prediction.toString());

        boolean resultat6 = service.terminerConsultation(cons2, "Je suis content de vous avoir vu !");
        printlnConsoleIHM(resultat6 + " -> Demande terminee ");

    }

    private static void testerConsulterListe() {
        Service service = new Service();
        List<Client> clients = service.listerClients();
        List<Employe> employes = service.listerEmployes();
        List<Medium> mediums = service.listerMediums();
        List<Consultation> consultations = service.listerConsultations();

        if (clients == null) {
            printlnConsoleIHM("ERREUR du Service listerClients");
        } else {
            printlnConsoleIHM("Liste des Clients (" + clients.size() + ")");
            for (Client c : clients) {
                printlnConsoleIHM("#" + c.getId() + " " + c.getNom().toUpperCase() + " " + c.getPrenom());
            }
            printlnConsoleIHM("----");
        }
        if (employes == null) {
            printlnConsoleIHM("ERREUR du Service listerEmployes");
        } else {
            printlnConsoleIHM("Liste des Employes (" + employes.size() + ")");
            for (Employe e : employes) {
                printlnConsoleIHM("#" + e.getId() + " " + e.getNom().toUpperCase() + " " + e.getPrenom());
            }
            printlnConsoleIHM("----");
        }
        if (mediums == null) {
            printlnConsoleIHM("ERREUR du Service listerMediums");
        } else {
            printlnConsoleIHM("Liste des Mediums (" + mediums.size() + ")");
            for (Medium m : mediums) {
                printlnConsoleIHM("#" + m.getId() + " " + m.getDenomination() + " " + m.getClass());
            }
            printlnConsoleIHM("----");
        }
        if (consultations == null) {
            printlnConsoleIHM("ERREUR du Service listerConsultations");
        } else {
            printlnConsoleIHM("Liste des Consultations (" + consultations.size() + ")");
            for (Consultation c : consultations) {
                printlnConsoleIHM("#" + c.getDate() + " Client : " + c.getClient().getPrenom() + " Medium : " + c.getMedium().getDenomination());
            }
            printlnConsoleIHM("----");
        }
    }

    public static void testerStatistiques() throws ParseException {
        Service service = new Service();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date dateNaissance = simpleDateFormat.parse("10/12/1995");

        Medium m1 = new Medium("medium 1", "astronomique", Genre.F);
        service.inscrireMedium(m1);
        Medium m2 = new Medium("medium 2", "astronomiquee", Genre.F);
        service.inscrireMedium(m2);

        Medium m3 = new Medium("medium 3", "astronomiqueee", Genre.F);
        service.inscrireMedium(m3);

        Medium m4 = new Medium("medium 4", "astronomiqueeee", Genre.F);
        service.inscrireMedium(m4);

        Medium m5 = new Medium("medium 5", "astronomiqueeeee", Genre.F);
        service.inscrireMedium(m5);

        Medium m6 = new Medium("medium 6", "astronomiqueeeeee", Genre.M);
        service.inscrireMedium(m6);

        Client c1 = new Client("Nicolas", "Cardenas", "nicolas.cardenas@blablab-lyon.fr", "esmeralda", "place bellecour", dateNaissance, "+330669099781", Genre.M);
        service.inscrireClient(c1);

        Employe e1 = new Employe("Hugette", "Marin", "hugo.marin@blablbaba.com", "esmeralda", "+330669099781", "+33645263258", Genre.F);
        service.inscrireEmploye(e1);
        e1 = service.authentifierEmploye("hugo.marin@blablbaba.com", "esmeralda");

        for (int i = 0; i < 10; i++) {
            service.creerConsultation(c1, m1);
            e1 = service.findEmploye(e1.getId());

            service.accepterConsultation(e1.getConsultationCourante());

            service.genererPrediction(c1, 2, 3, 4);

            service.terminerConsultation(e1.getConsultationEnCours(), "Je suis content de vous avoir vu !");
        }
        for (int i = 0; i < 6; i++) {
            service.creerConsultation(c1, m2);
            e1 = service.findEmploye(e1.getId());

            service.accepterConsultation(e1.getConsultationCourante());

            service.genererPrediction(c1, 2, 3, 4);

            service.terminerConsultation(e1.getConsultationEnCours(), "Je suis content de vous avoir vu !");
        }
        for (int i = 0; i < 5; i++) {
            service.creerConsultation(c1, m3);
            e1 = service.findEmploye(e1.getId());

            service.accepterConsultation(e1.getConsultationCourante());

            service.genererPrediction(c1, 2, 3, 4);

            service.terminerConsultation(e1.getConsultationEnCours(), "Je suis content de vous avoir vu !");
        }
        for (int i = 0; i < 3; i++) {
            service.creerConsultation(c1, m4);
            e1 = service.findEmploye(e1.getId());

            service.accepterConsultation(e1.getConsultationCourante());

            service.genererPrediction(c1, 2, 3, 4);

            service.terminerConsultation(e1.getConsultationEnCours(), "Je suis content de vous avoir vu !");
        }
        for (int i = 0; i < 2; i++) {
            service.creerConsultation(c1, m5);
            e1 = service.findEmploye(e1.getId());

            service.accepterConsultation(e1.getConsultationCourante());

            service.genererPrediction(c1, 2, 3, 4);

            service.terminerConsultation(e1.getConsultationEnCours(), "Je suis content de vous avoir vu !");
        }

        printlnConsoleIHM("Localisation du client : " + service.getClientLocalisation(c1));

        printlnConsoleIHM("Statistique : nb de consultations par mediums : " + service.getConsultationsPerMedium());

        printlnConsoleIHM("Statistique : top 5 des mediums par ordre croissant : " + service.getTop5SelectedMedium());

    }

    public static void printlnConsoleIHM(Object o) {
        String BG_CYAN = "\u001b[46m";
        String RESET = "\u001B[0m";

        System.out.print(BG_CYAN);
        System.out.println(String.format("%-80s", o));
        System.out.print(RESET);
    }

}
