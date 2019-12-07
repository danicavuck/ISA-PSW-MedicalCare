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
    public static final String ADMIN_KC_PRVI_PUT = "prvi_put_logovan";
    public static final String ADMIN_KC_AKTIVAN = "admin_kc_aktivan";

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
    public static final String REGISTRACIJA_AKTIVAN = "aktivan";

    // Klinika
    public static final String KLINIKA_ID = "id_klinike";
    public static final String KLINIKA_NAZIV = "naziv";
    public static final String KLINIKA_ADRESA = "adresa";
    public static final String KLINIKA_OPIS = "opis";
    public static final String KLINIKA_PROSECNA_OCENA = "prosecna_ocena";
    public static final String KLINIKA_ADMINI_KLINIKE = "admin_klinike";

    // Lekar
    public static final String LEKAR_ID = "id_lekar";
    public static final String LEKAR_IME = "ime";
    public static final String LEKAR_PREZIME = "prezime";
    public static final String LEKAR_EMAIL = "email";
    public static final String LEKAR_LOZINKA = "lozinka";
    public static final String LEKAR_PROSECNA_OCENA = "prosecna_ocena";
    public static final String LEKAR_KLINIKA = "klinika";

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

    // Sifarnik lekova
    public static final String SIFARNIK_LEKOVA_ID = "id_lek";
    public static final String SIFARNIK_LEKOVA_KOD_LEKA = "kod_leka";
    public static final String SIFARNIK_LEKOVA_NAZIV_LEKA = "naziv_leka";
    public static final String SIFARNIK_LEKOVA_AKTIVAN = "aktivan";

    // Sifarnik dijagnoza
    public static final String SIFARNIK_DIJAGNOZA_ID = "id_dijagnoza";
    public static final String SIFARNIK_DIJAGNOZA_KOD_BOLESTI = "kod_bolesti";
    public static final String SIFARNIK_DIJAGNOZA_NAZIV_BOLESTI = "naziv_bolesti";
    public static final String SIFARNIK_DIJAGNOZA_AKTIVAN = "aktivan";

    // Admin klinike
    public static final String ADMIN_KLINIKE_ID = "id_admin";
    public static final String ADMIN_KLINIKE_MEJL = "email";
    public static final String ADMIN_KLINIKE_LOZINKA = "lozinka";
    public static final String ADMIN_KLINIKE_IME = "ime";
    public static final String ADMIN_KLINIKE_PREZIME = "prezime";
    public static final String ADMIN_KLINIKE_KLINIKA = "klinika";
    public static final String ADMIN_KLINIKE_AKTIVAN = "aktivan";
    public static final String ADMIN_KLINIKE_PRVO_LOGOVANJE = "prvo_logovanje";

    // Lekar Pacijent - Join Table
    // Dodavanje se vrsi UVEK SA STRANE LEKARA! Nikad sa strane pacijenta!
    public static final String LEKAR_PACIJENT_LEKAR = "lekar_id";
    public static final String LEKAR_PACIJENT_PACIJENT = "pacijent_id";

    // Medicinska sestra Pacijent - Join Table
    // Dodavanje se vrsi UVEK SA STRANE MEDICINSKE SESTRE! Nikad sa strane pacijenta!
    public static final String MEDICINSKA_SESTRA_SESTRA = "med_sestra_id";
    public static final String MEDICINSKA_SESTRA_PACIJENT = "pacijent_id";




    private DbColumnConstants(){

    }
}
