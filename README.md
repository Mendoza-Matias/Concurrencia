# Concurrencia

## Primer ejemplo: Condición de carrera 
### Metodos para evitar la condición de carrera
- AtomicInteger
- ReentrantLock
- Synchronized

- En este ejemplo, se crea una clase `Counter` que tiene un método `increment()` para incrementar un contador. Luego, se crea una clase `Task` que implementa `Runnable` y llama al método `increment()` varias veces. En la clase `Main`, se crean múltiples hilos que ejecutan la tarea de incrementar el contador. Debido a la falta de sincronización, es posible que el valor final del contador no sea el esperado, lo que demuestra una condición de carrera y el tiempo que tarda en ejecutarse el programa puede variar cada vez que se ejecuta.

## Nota

- Un caso con AtomicInteger falla porque el método incrementAndGet() es atómico, lo que significa que garantiza que la operación de incremento se realice de manera indivisible. Sin embargo, si múltiples hilos están llamando a incrementAndGet() al mismo tiempo, es posible que algunos hilos vean un valor desactualizado del contador antes de que se actualice, lo que puede llevar a resultados inesperados. Esto se debe a que el método incrementAndGet() no garantiza la visibilidad de los cambios realizados por otros hilos, lo que puede resultar en una condición de carrera.

- Con ReentrantLock, se puede garantizar que solo un hilo pueda acceder al método increment() a la vez, lo que evita la condición de carrera y asegura que el valor del contador sea correcto. Sin embargo, el uso de ReentrantLock puede introducir una sobrecarga adicional debido a la necesidad de adquirir y liberar el bloqueo, lo que puede afectar el rendimiento del programa.

## Tiempo de ejecución

- AtomicInteger : 292ms | 261 ms (1000 hilos)
- ReentrantLock : 281ms | 267 ms (1000 hilos)
- Synchronized :  293ms |  270 ms (1000 hilos)
---
## Executor Service

```
 ExecutorService executor = Executors.newSingleThreadExecutor(); // Crea un executor con un solo hilo
 ExecutorService executor = Executors.newFixedThreadPool(5); // Crea un executor con 5 hilos
```

- Tiempo con un solo hilo: 1775306994798 | 1775307064122 
- Tiempo con 5 hilos: 1775307012249 | 1775307026193

## CompletableFuture

> Nota: las entidades del repositorio se pueden mejorar con interfaces para la inyección de dependencias y asi respetar el principio de inversion de dependencias. 

- El uso de supplyAsync permite ejecutar una tarea en un hilo separado, lo que evita que el hilo principal se bloquee y permite que el programa realice otras tareas mientras espera a que la tarea termine. 
- El uso de get() bloquea el hilo principal hasta que la tarea termine.

## Deadlock

- El deadlock es una situación en la que dos o más hilos están bloqueados esperando a que el otro libere el recurso que necesitan. 
Por ejemplo:
```java
        Object thread1 = new Object();
        Object thread2 = new Object();

        // Hilo 1
        new Thread(() -> {
            synchronized (thread1) { // Adquiere el lock del recurso 1
                System.out.println("El hilo 1 tiene el recurso 1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("El hilo 1 esperando el lock del hilo 2");
                synchronized (thread2) { // Intenta adquirir el lock del recurso 2 - no lo tiene porque el hilo 2 no ha entrado en el synchronized
                    System.out.println("El hilo 1 posee ambos locks");
                }
            }
        }).start();

        // Hilo 2
        new Thread(() -> {
            synchronized (thread2) { // Adquiere el lock del recurso 2
                System.out.println("El hilo 2 tiene el recurso 2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("El hilo 2 esperando el lock del hilo 1");
                synchronized (thread1) { // Intenta adquirir el lock del recurso 1 - no lo tiene porque el hilo 1 no ha entrado en el synchronized
                    System.out.println("El hilo 2 posee ambos locks");
                }
            }
        }).start();
```
Lo que pasa es que el hilo 1 adquiere el lock del recurso 1 y luego intenta adquirir el lock del recurso 2, pero no lo tiene porque el hilo 2 no ha entrado en el synchronized. Entonces el hilo 1 se queda esperando a que el hilo 2 libere el lock del recurso 2, pero el hilo 2 no puede liberar el lock del recurso 2 porque no lo tiene porque el hilo 1 no ha entrado en el synchronized. Y asi sucesivamente.

---
## Ejercicio practico (global):

# ⚔️ Ejercicio — Servidor de Partidas de League of Legends

> Simulación concurrente de una partida de LoL usando los conceptos de concurrencia de Java.

---

## Contexto

Estás construyendo el backend de un servidor de partidas de LoL.
Hay una partida en curso con **5 jugadores**: Ezreal, Jinx, Thresh, Ahri y Yasuo.

Cada jugador corre en su **propio hilo** y simultáneamente:
- Mata minions
- Ataca al Nexo enemigo
- Intenta atacar al Barón Nashor

---

## Qué tenés que implementar

### 1. Contador de minions global — `AtomicInteger`

Cada jugador mata minions en su propio hilo.
Todos suman al mismo contador compartido.
El contador nunca puede dar un resultado incorrecto aunque 5 hilos incrementen al mismo tiempo.

### 2. Nexo con vida compartida — `synchronized`

- El Nexo tiene **1000 HP**
- Múltiples jugadores pueden atacarlo al mismo tiempo
- El daño debe aplicarse atómicamente — nunca debe quedar en un valor inconsistente
- El HP **nunca puede quedar negativo**
- Cuando llega a 0, el juego termina

### 3. Barón Nashor como recurso exclusivo — `synchronized`

- El Barón tiene **9000 HP**
- Solo puede ser atacado por **un jugador a la vez**
- Si otro jugador llega mientras está siendo atacado, **espera**
- Cuando muere, otorga el buff a quien lo mató

### 4. Cada jugador corre en su propio hilo — `ExecutorService`

- Usá un pool de **5 hilos**
- Cada jugador ejecuta sus acciones en paralelo
- El programa **espera que todos terminen** antes de mostrar el resumen final

### 5. Resumen final — `CompletableFuture`

Cuando todos los jugadores terminaron, calculá el **MVP** (el que más minions mató)
de forma asíncrona y mostralo.

---

## Reglas del juego

| Acción | Detalle |
|--------|---------|
| Farmear minions | Cada jugador mata entre 10 y 30 minions (aleatorio) con `Thread.sleep(50ms)` por minion |
| Atacar el Nexo | Cada jugador ataca 3 veces con daño aleatorio entre 50 y 150 |
| Atacar el Barón | Cada jugador intenta una vez — si otro lo está atacando, espera su turno |

---

## Estructura de clases sugerida

```
src/
└── com/lol/
    ├── Main.java           ← arranca la partida
    ├── Game.java           ← estado global compartido
    ├── Nexus.java          ← recurso compartido con synchronized
    ├── BaronNashor.java    ← recurso exclusivo (1 jugador a la vez)
    └── Champion.java       ← cada jugador corre en su propio hilo (Runnable)
```

---

## Esqueleto de código

### `Game.java`
```java
public class Game {
    // Contador global — muchos hilos escriben al mismo tiempo
    private final AtomicInteger totalMinions = new AtomicInteger(0);

    // Minions por jugador — para calcular el MVP
    private final ConcurrentHashMap<String, Integer> minionsByChamp
        = new ConcurrentHashMap<>();

    public void addMinions(String champ, int amount) {
        totalMinions.addAndGet(amount);
        minionsByChamp.merge(champ, amount, Integer::sum);
    }

    public String getMVP() {
        return minionsByChamp.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("nadie");
    }

    // getters...
}
```

### `Nexus.java`
```java
public class Nexus {
    private int hp = 1000;

    public synchronized boolean takeDamage(String champ, int damage) {
        // TODO: aplicar daño, no bajar de 0, retornar true si murió
    }

    public synchronized boolean isDead() {
        return hp <= 0;
    }
}
```

### `BaronNashor.java`
```java
public class BaronNashor {
    private int hp    = 9000;
    private boolean alive = true;

    public synchronized void attack(String champ, int damage) {
        // TODO: si ya murió, no hacer nada
        // TODO: aplicar daño, si llega a 0 marcar muerto y dar buff
    }
}
```

### `Champion.java`
```java
public class Champion implements Runnable {
    private final String      name;
    private final Game        game;
    private final Nexus       nexus;
    private final BaronNashor baron;
    private final Random      random = new Random();

    public void run() {
        farmMinions();   // mata minions en loop
        attackNexus();   // ataca el nexo 3 veces
        attackBaron();   // intenta atacar al barón
    }

    private void farmMinions() { /* TODO */ }
    private void attackNexus() { /* TODO */ }
    private void attackBaron() { /* TODO */ }
}
```

### `Main.java`
```java
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Game        game  = new Game();
        Nexus       nexus = new Nexus();
        BaronNashor baron = new BaronNashor();

        List<String> names = List.of("Ezreal", "Jinx", "Thresh", "Ahri", "Yasuo");

        ExecutorService executor = Executors.newFixedThreadPool(5);

        // TODO: crear un Champion por cada nombre y submitirlo al executor

        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.MINUTES);

        // TODO: usar CompletableFuture para calcular y mostrar el MVP
    }
}
```

---

## Salida esperada

```
=== PARTIDA INICIADA ===
[Ezreal]  empieza a farmear...
[Jinx]    empieza a farmear...
[Thresh]  empieza a farmear...
[Ahri]    empieza a farmear...
[Yasuo]   empieza a farmear...
[Ezreal]  mató 23 minions  (total: 23)
[Ahri]    mató 17 minions  (total: 40)
[Yasuo]   mató 28 minions  (total: 68)
...
[Jinx]    está atacando al Barón Nashor...
[Ezreal]  esperando para atacar al Barón...
[Jinx]    hizo 2500 de daño al Barón  (HP: 6500)
[Ezreal]  hizo 1800 de daño al Barón  (HP: 4700)
...
☠  ¡El Nexo cayó! La partida terminó.

=== RESUMEN FINAL ===
Minions totales: 110
  Jinx:   30
  Yasuo:  28
  Ezreal: 23
  Ahri:   17
  Thresh: 12
🏆 MVP: Jinx (30 minions)
```

---

## Señales de que tu solución es correcta ✅

- El total de minions es **exactamente** la suma de los individuales — sin pérdidas
- El HP del Nexo **nunca queda negativo**
- Los mensajes del Barón muestran que los jugadores **se turnan**, no se pisan
- El resumen aparece **después** de que todos los hilos terminaron
- El MVP siempre es el que más minions tiene

## Señales de que hay un bug de concurrencia ❌

- El total de minions **no coincide** con la suma individual → race condition en el contador
- El HP del Nexo **quedó negativo** → falta sincronización
- Dos jugadores atacan al Barón **al mismo tiempo** en el log → falta `synchronized`
- El MVP aparece **antes** de que todos terminen → falta `awaitTermination`

---

## Concepto que practica cada parte

| Parte | Concepto | Por qué |
|-------|----------|---------|
| Minions | `AtomicInteger` | Una sola variable, operación simple de incremento |
| Nexo | `synchronized` | Estado mutable compartido, invariante: HP ≥ 0 |
| Barón | `synchronized` | Recurso exclusivo, un acceso a la vez |
| Partida | `ExecutorService` | 5 hilos corriendo en paralelo |
| MVP | `CompletableFuture` | Resultado asíncrono calculado al final |