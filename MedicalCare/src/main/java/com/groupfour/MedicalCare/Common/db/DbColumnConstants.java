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
    public static final String PACIJENT_KARTON = "karton_id";

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

    // Izvestaj o pregledu
    public static final String IZVESTAJ_O_PREGLEDU_ID = "izvestaj_o_pregledu_id";
    public static final String IZVESTAJ_O_PREGLEDU_INFORMACIJE = "informacije";
    public static final String IZVESTAJ_O_PREGLEDU_AKTIVAN = "aktivan";
    public static final String IZVESTAJ_O_PREGLEDU_DIJAGNOZA = "dijagnoza_id";
    public static final String IZVESTAJ_O_PREGLEDU_LEK = "sifarnik_lekova_id";

    // Recept
    public static final String RECEPT_ID = "recept_id";
    public static final String RECEPT_ID_LEKA = "id_leka";
    public static final String RECEPT_OVEREN = "oeveren";
    public static final String RECEPT_AKTIVAN = "recept_aktivan";
    public static final String RECEPT_MEDICINSKA_SESTRA = "med_sestra_id";
    public static final String RECEPT_LEKAR = "lekar_id";

    // Operacija
    public static final String OPERACIJA_ID = "id_operacija";
    public static final String OPERACIJA_TERMIN = "termin";
    public static final String OPERACIJA_AKTIVNA = "aktivna";
    public static final String OPERACIJA_SALA = "sala";
    public static final String OPERACIJA_PACIJENT = "pacijent";

    // Odsustvo
    public static final String ODSUSTVO_ID = "id_odsustva";
    public static final String ODSUSTVO_POCETAK = "pocetak_odsustva";
    public static final String ODSUSTVO_KRAJ = "kraj_odsustva";
    public static final String ODSUSTVO_ODOBRRENO = "odobreno";
    public static final String ODSUSTVO_AKTIVNO = "aktivno";

    // Tip pregleda
    public static final String TIP_PREGLEDA_ID = "tip_pregleda_id";
    public static final String TIP_PREGLEDA_PREDEFINISAN = "predefinisan";
    public static final String TIP_PREGLEDA_TEKST = "tip_pregleda";
    public static final String TIP_PREGLEDA_AKTIVAN = "aktivan";

    // Pregled
    public static final String PREGLED_ID = "pregled_id";
    public static final String PREGLED_TERMIN = "termin";
    public static final String PREGLED_TRAJANJE = "trajanje";
    public static final String PREGLED_CENA = "cena";
    public static final String PREGLED_POPUST = "popust";
    public static final String PREGLED_AKTIVAN = "aktivan";
    public static final String PREGLED_SALA = "sala";
    public static final String PREGLED_IZVESTAJ = "id_izvestaja";
    public static final String PREGLED_TIP_PREGLEDA = "tip_id";
    public static final String PREGLED_PACIJENT = "pacijent_id";

    // Ocena Lekara
    public static final String OCENA_LEKARA_ID = "ocena_id";
    public static final String OCENA_LEKARA_OCENA = "ocena";
    public static final String OCENA_LEKARA_AKTIVAN = "aktivna";
    public static final String OCENA_LEKARA_ID_LEKARA = "lekar_id";
    public static final String OCENA_LEKARA_ID_PACIJENTA = "pacijent_id";

    // Ocena klinike
    public static final String OCENA_KLINIKE_ID = "ocena_id";
    public static final String OCENA_KLINIKE_OCENA = "ocena";
    public static final String OCENA_KLINIKE_AKTIVAN = "aktivan";
    public static final String OCENA_KLINIKE_KLINIKA = "klinika_id";
    public static final String OCENA_KLINIKE_PACIJENT = "pacijent_id";

    // Karton
    public static final String KARTON_ID = "karton_id";
    public static final String KARTON_AKTIVAN = "aktivan";


    private DbColumnConstants(){

    }
}
