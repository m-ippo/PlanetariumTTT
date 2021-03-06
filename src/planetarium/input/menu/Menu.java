package planetarium.input.menu;

import planetarium.contents.corpicelesti.interfaces.CorpoCeleste;
import planetarium.contents.registro.Registro;
import planetarium.contents.system.GestioneSistema;
import planetarium.contents.system.utils.OutputPicker;
import planetarium.input.Formattazione;
import planetarium.input.GestioneInput;
import planetarium.input.InputDomande;
import planetarium.input.InputOggetti;
import planetarium.input.menu.utili.Coppia;
import planetarium.input.menu.utili.FuturaAzioneMenu;

import java.util.ArrayList;
import planetarium.contents.corpicelesti.enums.TipiCorpiCelesti;
import planetarium.input.menu.utili.Aiuto;
import planetarium.input.menu.utili.TreeSystem;

public class Menu {

    private final ArrayList<Coppia<String, FuturaAzioneMenu>> menu;
    private final ArrayList<FuturaAzioneMenu> esegui_dopo_menu;

    private boolean death_star_unlocked = false;
    private int death_star_pos = -1;

    public Menu() {
        this.menu = new ArrayList<>();
        this.esegui_dopo_menu = new ArrayList<>();
        init();
    }

    private void init() {
        menu.clear();
        esegui_dopo_menu.clear();
        death_star_pos = -1;
        death_star_unlocked = false;
        Formattazione.printOut("\t\tBENVENUTO NEL PLANETARIUM!", true, false);
        Formattazione.incrementaIndentazioni();
        menu.add(new Coppia<>("Esci", () -> {
            Formattazione.printOut("Arrivederci!!!", true, false);
            System.exit(0);
        }));
        menu.add(new Coppia<>("Visualizza aiuto", () -> {
            Aiuto.getIstance().printHelp();
        }));
        if (GestioneSistema.getIstance() != null) {
            aggiungiOpzioniBase();
        } else {
            menu.add(new Coppia<>("Crea il sistema ", () -> {
                GestioneSistema lgs = InputOggetti.leggiGestioneSistema();
                if (lgs != null) {
                    esegui_dopo_menu.add(() -> {
                        menu.remove(2);
                        aggiungiOpzioniBase();
                    });
                }
            }));
        }
    }

    private void aggiungiOpzioniBase() {
        aggiungiOpzione("Mostra registro", () -> {
            Formattazione.printOut("Registro: ", true, false);
            Formattazione.incrementaIndentazioni();
            Registro.stampaRegistro();
            Formattazione.decrementaIndentazioni();
        });
        aggiungiOpzione("Mostra sistema", () -> {
            TreeSystem ts = new TreeSystem();
            ts.printTree();
        });
        aggiungiOpzione("Inserisci un corpo celeste", () -> {
            CorpoCeleste cc = InputOggetti.leggiCorpoCeleste();
            if (cc != null) {
                Formattazione.printOut("Corpo registrato correttamente ", true, false);
                Formattazione.printOut(OutputPicker.getIstance().getOnCreate(), true, false);
                if (!death_star_unlocked && cc.getTipo() == TipiCorpiCelesti.LUNA) {
                    System.err.println("Obiettivo sbloccato: Morte Nera!");
                    death_star_unlocked = true;
                    death_star_pos = menu.size();
                    aggiungiOpzione("Morte Nera", () -> {
                        InputOggetti.gestisceMorteNera();
                    });
                }
            } else {
                Formattazione.printOut("Errore nella registrazione ", true, true);
            }
        });
        aggiungiOpzione("Rimuovi corpo celeste", () -> {
            CorpoCeleste rimuovi = InputDomande.ricercaCorpoCeleste();
            if (rimuovi != null) {
                Boolean risposta = GestioneInput.leggiBoolean("Sei sicuro di voler eliminare \"" + rimuovi.toString() + "\"? (Tutti i corpi sulle sue orbite verranno eliminati) ");
                if (risposta) {
                    rimuovi.distruggi();
                    Formattazione.printOut(rimuovi.getNome() + " eliminato correttamente! ", true, false);
                    Formattazione.printOut(OutputPicker.getIstance().getOnDelete(), true, false);
                    switch (rimuovi.getTipo()) {
                        case STELLA:
                            GestioneSistema.destroy();
                            init();
                            break;
                        case MORTE_NERA:
                            esegui_dopo_menu.add(() -> {
                                menu.remove(death_star_pos);
                            });
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        aggiungiOpzione("Mostra luna di un pianeta ", () -> {
            InputDomande.stampaLune();
        });
        aggiungiOpzione("Visualizza gerarchia corpo celeste ", () -> {
            InputDomande.gerarchia();
        });
        aggiungiOpzione("Visualizza percorso tra due punti ", () -> {
            InputDomande.percorso();
        });

        aggiungiOpzione("Trova corpo celeste", () -> {
            InputDomande.mostraCC();
        });
        aggiungiOpzione("Calcola somma delle masse e somma pesata delle posizioni", () -> {
            InputDomande.calcolaMassa();
        });
        aggiungiOpzione("Calcola collisioni ", () -> {
            InputDomande.controllaCollisioni();
        });
    }

    public void aggiungiOpzione(String voce, FuturaAzioneMenu azione) {
        if (voce != null && azione != null) {
            menu.add(new Coppia<>(voce, azione));
        }
    }

    public void stampaMenu() {
        stampaSpazi(3);
        for (int i = 0; i < menu.size(); i++) {
            Formattazione.printOut("[" + (i + 1) + "] " + menu.get(i).getChiave(), true, false);
        }
        System.out.println();
        Formattazione.incrementaIndentazioni();
        int operazioneScelta;
        do {
            operazioneScelta = GestioneInput.leggiInteger("Inserisci operazione: ");
        } while (operazioneScelta < 1 || operazioneScelta > menu.size());
        Formattazione.decrementaIndentazioni();
        stampaSpazi(2);
        eseguiOperazione(operazioneScelta - 1);
    }

    private void stampaSpazi(int n_spazi) {
        for (int i = 0; i < n_spazi; i++) {
            System.out.println();
        }
    }

    private void eseguiOperazione(int operazione) {
        menu.get(operazione).getValore().onSelezionato();
        esegui_dopo_menu.forEach(FuturaAzioneMenu::onSelezionato);
        esegui_dopo_menu.clear();
        stampaMenu();
    }

}
