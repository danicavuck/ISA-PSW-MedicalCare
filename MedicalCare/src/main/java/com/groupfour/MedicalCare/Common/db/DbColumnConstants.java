package com.groupfour.MedicalCare.Common.db;

public final class DbColumnConstants {
    // pacijent
    public static final String PACIJENT_ID = "id_pacijent";
    public static final String PACIJENT_EMAIL = "email";
    public static final String PACIJENT_LOZINKA = "lozinka";
    public static final String PACIJENT_IME = "ime";
    public static final String PACIJENT_PREZIME = "prezime";
    public static final String PACIJENT_ADRESA = "adresa";
    public static final String PACIJENT_GRAD = "grad";
    public static final String PACIJENT_DRZAVA = "drzava";
    public static final String PACIJENT_TELEFON = "telefon";
    public static final String PACIJENT_BROJOSIGURANJA = "broj_osiguranja";

    // admin_kc
    public static final String ADMIN_KC_ID = "id_admin_kc";
    public static final String ADMIN_KC_IME = "ime";
    public static final String ADMIN_KC_PREZIME = "prezime";
    public static final String ADMIN_KC_EMAIL = "email";
    public static final String ADMIN_KC_LOZINKA = "lozinka";

    // zahtevi za registraciju
    public static final String REGISTRACIJA_ID = "id_registracija";
    public static final String REGISTRACIJA_EMAIL = "email";
    public static final String REGISTRACIJA_LOZINKA = "lozinka";
    public static final String REGISTRACIJA_IME = "ime";
    public static final String REGISTRACIJA_PREZIME = "prezime";
    public static final String REGISTRACIJA_ADRESA = "adresa";
    public static final String REGISTRACIJA_GRAD = "grad";
    public static final String REGISTRACIJA_DRZAVA = "drzava";
    public static final String REGISTRACIJA_TELEFON = "telefon";
    public static final String REGISTRACIJA_OSIGURANJE = "osiguranje";
    public static final String REGISTRACIJA_ODOBREN = "odobren";
    public static final String REGISTRACIJA_RAZLOG_ODBIJANJA = "razlog_odbijanja";
    public static final String REGISTRACIJA_ADMIN_KC = "admin_kc_fk";

    // Klinika
    public static final String KLINIKA_ID = "id_klinike";
    public static final String KLINIKA_NAZIV = "naziv";
    public static final String KLINIKA_ADRESA = "adresa";
    public static final String KLINIKA_OPIS = "opis";
    public static final String KLINIKA_PROSECNA_OCENA = "prosecna_ocena";

    // Lekar
    public static final String LEKAR_ID = "id_lekar";
    public static final String LEKAR_IME = "ime";
    public static final String LEKAR_PREZIME = "prezime";
    public static final String LEKAR_EMAIL = "email";
    public static final String LEKAR_LOZINKA = "lozinka";
    public static final String LEKAR_PROSECNA_OCENA = "prosecna_ocena";

    // Medicninska Sestra
    public static final String MEDICINSKA_SESTRA_ID = "id_med_sestra";
    public static final String MEDICINSKA_SESTRA_IME = "ime";
    public static final String MEDICINSKA_SESTRA_PREZIME = "prezime";
    public static final String MEDICINSKA_SESTRA_EMAIL = "email";
    public static final String MEDICINSKA_SESTRA_LOZINKA = "lozinka";

    // Sala
    public static final String SALA_ID = "id_sala";
    public static final String SALA_ZAUZECE = "zauzeta";
    public static final String SALA_POCETAK_TERMINA = "pocetak_termina";
    public static final String SALA_ZAVRSETAK_TERMINA = "zavrsetak_termina";
    public static final String SALA_BROJ_SALE = "broj_sale";



    private DbColumnConstants(){

    }
}
