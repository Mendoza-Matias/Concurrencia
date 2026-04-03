# Concurrencia

## Primer ejemplo: Condición de carrera
- En este ejemplo, se crea una clase `Counter` que tiene un método `increment()` para incrementar un contador. Luego, se crea una clase `Task` que implementa `Runnable` y llama al método `increment()` varias veces. En la clase `Main`, se crean múltiples hilos que ejecutan la tarea de incrementar el contador. Debido a la falta de sincronización, es posible que el valor final del contador no sea el esperado, lo que demuestra una condición de carrera y el tiempo que tarda en ejecutarse el programa puede variar cada vez que se ejecuta.

## Nota

- Un caso con AtomicInteger falla porque el método incrementAndGet() es atómico, lo que significa que garantiza que la operación de incremento se realice de manera indivisible. Sin embargo, si múltiples hilos están llamando a incrementAndGet() al mismo tiempo, es posible que algunos hilos vean un valor desactualizado del contador antes de que se actualice, lo que puede llevar a resultados inesperados. Esto se debe a que el método incrementAndGet() no garantiza la visibilidad de los cambios realizados por otros hilos, lo que puede resultar en una condición de carrera.

- Con ReentrantLock, se puede garantizar que solo un hilo pueda acceder al método increment() a la vez, lo que evita la condición de carrera y asegura que el valor del contador sea correcto. Sin embargo, el uso de ReentrantLock puede introducir una sobrecarga adicional debido a la necesidad de adquirir y liberar el bloqueo, lo que puede afectar el rendimiento del programa.

## Tiempo de ejecución

- No hay diferencias significativas en el tiempo de ejecución entre los 3 ejemplos.
---