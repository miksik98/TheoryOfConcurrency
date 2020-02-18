
function printAsync(s, cb) {
    var delay = Math.floor((Math.random()*1000)+500);
    setTimeout(function() {
        console.log(s);
        if (cb) cb();
    }, delay);
}

// Napisz funkcje (bez korzytania z biblioteki async) wykonujaca 
// rownolegle funkcje znajdujace sie w tablicy 
// parallel_functions. Po zakonczeniu wszystkich funkcji
// uruchamia sie funkcja final_function. Wskazowka:  zastosowc 
// licznik zliczajacy wywolania funkcji rownoleglych 

function inparallel(parallel_functions, final_function) {

    for(var counter_parallel = 0; counter_parallel < parallel_functions.length-1; counter_parallel++){
        parallel_functions[counter_parallel]();
    }
    parallel_functions[parallel_functions.length-1](()=>{final_function()});


}

A=function(cb){printAsync("A",cb);}
B=function(cb){printAsync("B",cb);}
C=function(cb){printAsync("C",cb);}
D=function(cb){printAsync("Done",cb);}
inparallel([A,B,C],D)

// kolejnosc: A, B, C - dowolna, na koncu zawsze "Done" 