package com.zoomtrack.croquis;

/**
 * Created by Andoresu on 14/05/2017.
 */

public class Constants {


    public static int[] car_resources200 =
               {R.drawable.bicicleta_200, R.drawable.bicicleta_volcada_200, R.drawable.bus_200, R.drawable.bus_volcado_200,
                R.drawable.camion_200, R.drawable.camion_volcado_200, R.drawable.camion_grande_200, R.drawable.camion_grande_volcado_200,
                R.drawable.camioneta_200, R.drawable.carro_200, R.drawable.montacarga_200, R.drawable.montacarga_volcado_200,
                R.drawable.motocicleta_200, R.drawable.motoclicleta_volcada_200, R.drawable.vehiculo_traccion_animal_200, R.drawable.vehiculo_traccion_animal_volcado_200,
                R.drawable.animal_traccion_muerto_200};

    public static float[] car_scales = {0.5f, 1.0f, 1.0f, 1.8f,
                                        1.0f, 1.3f, 1.0f, 1.4f,
                                        1.0f, 1.0f, 1.0f, 1.3f,
                                        0.5f, 0.8f, 0.5f, 0.8f,
                                        1.0f};

    public static String[] car_titles = {"Bicicleta", "Bicicleta volcada", "Bus", "Bus volcado",
            "Camion", "Camion volcado", "Camion grande", "Camion grande volcado",
            "Camioneta", "Carro", "Montacarga", "Montacarga volcado",
            "Motocileta", "Motocicleta volcada", "Vehiculo de traccion animal", "Vehiculo de traccion animal volcado",
            "Animal de traccion muerto"};

    public static int[] victim_resources = {
            R.drawable.victima_pose_1, R.drawable.victima_pose_2, R.drawable.victima_pose_3, R.drawable.victima_pose_4
    };

    public static String[] victim_titles = {
            "Victima pose 1", "Victima pose 2", "Victima pose 3", "Victima pose 4"
    };

    public static float[] victim_scales = {
            1.0f, 1.0f, 1.0f, 1.0f
    };


    public static int[] object_resources = {
            R.drawable.muro, R.drawable.puerta, R.drawable.puerta_cerrada, R.drawable.ventana,
            R.drawable.rio, R.drawable.cerca_alambre_puas, R.drawable.cerca_alambre_liso, R.drawable.poste_tranformador,
            R.drawable.poste_telefono, R.drawable.poste_luz, R.drawable.poste_luz_doble, R.drawable.alcantarilla,
            R.drawable.hidrante, R.drawable.arbol_1,R.drawable.arbol_2, R.drawable.arbol_3,
            R.drawable.arbol_derribado, R.drawable.semaforo_vehicular, R.drawable.semaforo_peatonal, R.drawable.rejilla_alcantarillado,
            R.drawable.hueco
    };

    public static String[] object_titles = {
            "Muro", "Puerta", "Puerta Cerrada", "Ventana",
            "Río", "Cerca de alambre de púas", "Cerca de alambre liso", "Poste de transformador",
            "Poste de teléfono", "Poste de luz", "Poste de luz doble", "Alcantarilla",
            "Hidrante", "Arbol 1", "Arbol 2", "Arbol 3",
            "Arbol derribado", "Semáforo vehícular", "Semáforo peatonal", "Rejilla y alcantarillado",
            "Hueco"
    };

    public static float[] object_scales = {
            2.0f, 2.0f, 2.0f, 2.0f,
            1.0f, 4.0f, 4.0f, 0.5f,
            0.5f, 0.5f, 0.5f, 1.0f,
            1.0f, 1.8f, 1.8f, 1.8f,
            2.5f, 0.8f, 0.8f, 0.5f,
            1.0f
    };

    public static int[] signal_resources = {
            R.drawable.trayectoria, R.drawable.sentido_vial, R.drawable.sentido_doble_vial, R.drawable.senal_transito
    };

    public static String[] siganl_titles = {
            "Trayectoria del vehículo", "Sentido vial", "Sentido vial doble", "Señal de transito"
    };

    public static float[] signal_scales = {
            1.0f, 1.0f, 1.0f, 1.0f
    };

    public static int[] trace_resources = {
            R.drawable.huella_frenado, R.drawable.huella_frenado_dobles, R.drawable.arrastre_metalico_peaton, R.drawable.arrastre_llanta,
            R.drawable.huella_trayectoria, R.drawable.punto_impacto
    };

    public static String[] trace_titles = {
            "Huella de frenado", "Huella de frenado 2", "Arraster metalico o de peaton", "Arrastre de llanta",
            "Huella de trayectoria", "Impacto"
    };

    public static float[] trace_scales = {
            0.7f, 0.7f, 0.2f, 0.5f,
            3.0f, 1.0f
    };


    public static String RESOURCES_200    = "CROQUIS_RESOURCES_200";
    public static String RESOURCES_TITLE  = "CROQUIS_RESOURCES_TITLE";
    public static String RESOURCES_SCALE = "CROQUIS_RESOURCES_SCALES";
    public static String MEASUREMENT_ELEMENTS = "CROQUIS_MEASUREMENT_ELEMENTS";

}
