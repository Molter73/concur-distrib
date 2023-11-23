@startuml
class Server {
    -{static} DEFAULT_PORT: int
    +{static} main(String[]): void
}

class Worker {
    -socket: Socket
    -input: BufferedReader
    -output: PrintWriter
    +Worker(Socket)
    -menu(): Options
    +run(): void
    -runActivity(Activity): void
}

interface Activity {
   +init(): String
   +run(String): String
   +getStatus(): boolean
}

class Echo implements Activity

class Fortune implements Activity {
    -{static} FORTUNES: String[]
    -{static} SIZE: int
    -{static} RANDOM: Random
    -running: boolean
    -getRandom(): String
}

class RockPaperScissor implements Activity {
    -menuOutput: String
    -running: boolean
    -{static} VALUES: List
    -{static} SIZE: int
    -{static} RANDOM: Random
    -{static} OUTCOME: int[][]
    -menu(): String
    -game(String): String
}

class Pokedex implements Activity {
    -running: boolean
    -{static} pokemons: ArrayList
    -loadPokemon(): ArrayList
    -getPokemon(int): String
}

Server -- Worker
Worker *-- Activity

@enduml
