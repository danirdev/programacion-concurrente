package tp7.actividad2;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 🔐 AlgoritmoClasico - Implementación de algoritmos clásicos de exclusión mutua
 * 
 * Esta clase implementa los algoritmos clásicos de exclusión mutua para
 * demostrar sus problemas y compararlos con la solución moderna de semáforos.
 * Incluye: 1 Flag, 2 Flags, y Peterson.
 * 
 * @author Estudiante Programación Concurrente 2025
 * @version 1.0
 */
public class AlgoritmoClasico {
    
    /**
     * 🚩 Algoritmo de 1 Flag - DEFECTUOSO (no garantiza exclusión mutua)
     */
    public static class UnFlag {
        private volatile boolean flag = false;
        private volatile int procesosEnRegion = 0;
        private volatile int violaciones = 0;
        
        public boolean entrar(String procesoId) {
            System.out.printf("[%s] 🚩 Algoritmo 1 Flag - Verificando flag%n", procesoId);
            
            // ❌ PROBLEMA: Verificación y asignación no son atómicas
            while (flag) {
                // Busy wait
                Thread.yield();
            }
            
            flag = true;
            
            // Verificar violación de exclusión mutua
            procesosEnRegion++;
            if (procesosEnRegion > 1) {
                violaciones++;
                System.err.printf("❌ [%s] VIOLACIÓN: %d procesos en región crítica%n", 
                                procesoId, procesosEnRegion);
                return false;
            }
            
            System.out.printf("[%s] ✅ Entrando en región crítica (1 Flag)%n", procesoId);
            return true;
        }
        
        public void salir(String procesoId) {
            procesosEnRegion--;
            flag = false;
            System.out.printf("[%s] 🔓 Saliendo de región crítica (1 Flag)%n", procesoId);
        }
        
        public int getViolaciones() { return violaciones; }
        public void resetear() { flag = false; procesosEnRegion = 0; violaciones = 0; }
    }
    
    /**
     * 🚩🚩 Algoritmo de 2 Flags - Mejora pero con riesgo de deadlock
     */
    public static class DosFlags {
        private final AtomicBoolean flag1 = new AtomicBoolean(false);
        private final AtomicBoolean flag2 = new AtomicBoolean(false);
        private volatile int procesosEnRegion = 0;
        private volatile int violaciones = 0;
        private volatile int deadlocks = 0;
        
        public boolean entrar(String procesoId, int numeroProc) {
            AtomicBoolean miFlag = (numeroProc == 1) ? flag1 : flag2;
            AtomicBoolean suFlag = (numeroProc == 1) ? flag2 : flag1;
            
            System.out.printf("[%s] 🚩🚩 Algoritmo 2 Flags - Solicitando acceso%n", procesoId);
            
            miFlag.set(true); // Indicar intención de usar el recurso
            
            long tiempoInicio = System.currentTimeMillis();
            while (suFlag.get()) {
                // Busy wait
                Thread.yield();
                
                // Detectar posible deadlock (espera > 1 segundo)
                if (System.currentTimeMillis() - tiempoInicio > 1000) {
                    deadlocks++;
                    System.err.printf("⚠️ [%s] DEADLOCK detectado - Liberando flag%n", procesoId);
                    miFlag.set(false);
                    try { Thread.sleep(10); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    miFlag.set(true);
                    tiempoInicio = System.currentTimeMillis();
                }
            }
            
            // Verificar exclusión mutua
            procesosEnRegion++;
            if (procesosEnRegion > 1) {
                violaciones++;
                System.err.printf("❌ [%s] VIOLACIÓN: %d procesos en región crítica%n", 
                                procesoId, procesosEnRegion);
                return false;
            }
            
            System.out.printf("[%s] ✅ Entrando en región crítica (2 Flags)%n", procesoId);
            return true;
        }
        
        public void salir(String procesoId, int numeroProc) {
            AtomicBoolean miFlag = (numeroProc == 1) ? flag1 : flag2;
            
            procesosEnRegion--;
            miFlag.set(false);
            System.out.printf("[%s] 🔓 Saliendo de región crítica (2 Flags)%n", procesoId);
        }
        
        public int getViolaciones() { return violaciones; }
        public int getDeadlocks() { return deadlocks; }
        public void resetear() { 
            flag1.set(false); 
            flag2.set(false); 
            procesosEnRegion = 0; 
            violaciones = 0; 
            deadlocks = 0; 
        }
    }
    
    /**
     * 🎯 Algoritmo de Peterson - Solución correcta pero con busy wait
     */
    public static class Peterson {
        private final AtomicBoolean flag1 = new AtomicBoolean(false);
        private final AtomicBoolean flag2 = new AtomicBoolean(false);
        private final AtomicInteger turno = new AtomicInteger(1);
        private volatile int procesosEnRegion = 0;
        private volatile int violaciones = 0;
        private volatile int busyWaitCiclos = 0;
        
        public boolean entrar(String procesoId, int numeroProc) {
            AtomicBoolean miFlag = (numeroProc == 1) ? flag1 : flag2;
            AtomicBoolean suFlag = (numeroProc == 1) ? flag2 : flag1;
            int suTurno = (numeroProc == 1) ? 2 : 1;
            
            System.out.printf("[%s] 🎯 Algoritmo Peterson - Solicitando acceso%n", procesoId);
            
            miFlag.set(true);           // Indicar intención
            turno.set(suTurno);         // Ceder el turno al otro proceso
            
            // Esperar hasta que sea mi turno O el otro proceso no quiera entrar
            while (suFlag.get() && turno.get() == suTurno) {
                busyWaitCiclos++;
                Thread.yield(); // Busy wait (problema del algoritmo)
            }
            
            // Verificar exclusión mutua
            procesosEnRegion++;
            if (procesosEnRegion > 1) {
                violaciones++;
                System.err.printf("❌ [%s] VIOLACIÓN: %d procesos en región crítica%n", 
                                procesoId, procesosEnRegion);
                return false;
            }
            
            System.out.printf("[%s] ✅ Entrando en región crítica (Peterson)%n", procesoId);
            return true;
        }
        
        public void salir(String procesoId, int numeroProc) {
            AtomicBoolean miFlag = (numeroProc == 1) ? flag1 : flag2;
            
            procesosEnRegion--;
            miFlag.set(false);
            System.out.printf("[%s] 🔓 Saliendo de región crítica (Peterson)%n", procesoId);
        }
        
        public int getViolaciones() { return violaciones; }
        public int getBusyWaitCiclos() { return busyWaitCiclos; }
        public void resetear() { 
            flag1.set(false); 
            flag2.set(false); 
            turno.set(1); 
            procesosEnRegion = 0; 
            violaciones = 0; 
            busyWaitCiclos = 0; 
        }
    }
    
    /**
     * 🧪 Proceso de prueba para algoritmos clásicos
     */
    public static class ProcesoPrueba extends Thread {
        private final String algoritmo;
        private final String procesoId;
        private final int numeroProc;
        private final int numeroAccesos;
        private final Object algoritmoObj;
        
        private int accesosExitosos = 0;
        private int accesosFallidos = 0;
        private long tiempoTotal = 0;
        
        public ProcesoPrueba(String algoritmo, String procesoId, int numeroProc, 
                           int numeroAccesos, Object algoritmoObj) {
            this.algoritmo = algoritmo;
            this.procesoId = procesoId;
            this.numeroProc = numeroProc;
            this.numeroAccesos = numeroAccesos;
            this.algoritmoObj = algoritmoObj;
            this.setName("Proceso-" + procesoId + "-" + algoritmo);
        }
        
        @Override
        public void run() {
            long tiempoInicio = System.currentTimeMillis();
            
            for (int i = 0; i < numeroAccesos && !Thread.currentThread().isInterrupted(); i++) {
                try {
                    boolean exitoso = false;
                    
                    // Ejecutar según el algoritmo
                    switch (algoritmo) {
                        case "1Flag":
                            UnFlag unFlag = (UnFlag) algoritmoObj;
                            exitoso = unFlag.entrar(procesoId);
                            if (exitoso) {
                                Thread.sleep(50); // Simular trabajo
                                unFlag.salir(procesoId);
                            }
                            break;
                            
                        case "2Flags":
                            DosFlags dosFlags = (DosFlags) algoritmoObj;
                            exitoso = dosFlags.entrar(procesoId, numeroProc);
                            if (exitoso) {
                                Thread.sleep(50); // Simular trabajo
                                dosFlags.salir(procesoId, numeroProc);
                            }
                            break;
                            
                        case "Peterson":
                            Peterson peterson = (Peterson) algoritmoObj;
                            exitoso = peterson.entrar(procesoId, numeroProc);
                            if (exitoso) {
                                Thread.sleep(50); // Simular trabajo
                                peterson.salir(procesoId, numeroProc);
                            }
                            break;
                    }
                    
                    if (exitoso) {
                        accesosExitosos++;
                    } else {
                        accesosFallidos++;
                    }
                    
                    // Pausa entre accesos
                    Thread.sleep(10 + (int)(Math.random() * 20));
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            
            tiempoTotal = System.currentTimeMillis() - tiempoInicio;
            System.out.printf("🏁 [%s] %s completado - Exitosos: %d, Fallidos: %d, Tiempo: %dms%n",
                            procesoId, algoritmo, accesosExitosos, accesosFallidos, tiempoTotal);
        }
        
        public double[] getResultados() {
            double tasaExito = numeroAccesos > 0 ? (double) accesosExitosos / numeroAccesos * 100 : 0;
            return new double[]{accesosExitosos, accesosFallidos, tiempoTotal, tasaExito};
        }
    }
}
