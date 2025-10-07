# Actividad 2 - Planta de Ensamblado de Dispositivos

Una planta de ensamblado de dispositivos posee 3 mesas donde cada una permite el ensamblado de un único dispositivo al mismo tiempo pero secuencialmente. El armado de un dispositivo transita por 3 fases:

- **Fase 1**: requiere el empleo de un destornillador y una pinza
- **Fase 2**: requiere el empleo de dos sargentos  
- **Fase 3**: requiere el empleo de dos pinzas

Cada Fase si bien independientes, se realizan en la misma mesa y en el orden mencionado (Fase 1, 2 y 3), y como habrá observado, con herramientas distintas.

## Recursos disponibles:
- 100 componentes y las empresas solo cuenta con:
  - 4 pinzas
  - 2 destornilladores  
  - 4 sargentos

## Tiempos de procesamiento:
- **Fase 1**: T (donde T = 400ms)
- **Fase 2**: ½ de T (200ms)
- **Fase 3**: 2T (800ms)

## Objetivo:
Simular la situación empleando semáforos y mostrar por pantalla cada etapa del proceso indicando el estado de avance de cada componente a medida que transita por las distintas etapas.

## Puntos a evaluar:
a. Compilación sin errores.
b. Uso correcto de semáforos para controlar el acceso a recursos.
c. Simulación del proceso de ensamblado con los tiempos especificados.
d. Mostrar el progreso de cada componente en cada fase.