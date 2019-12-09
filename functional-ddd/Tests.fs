module Tests

type Address = {
    Street1: string
    Street2: Option<string>
    Zip: int
 }

let addr = { Street1 = "58 Kenyon Street SE"; Street2 = None; Zip = 30316; }

printf "Hello World"