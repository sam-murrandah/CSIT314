
mod tradespeople;
mod tradeprofessional;
mod userInterface;

use userInterface::*;
use tradespeople::*;




fn main() {
    let mut TradesPeople = Tradespeople::new();
    TradesPeople.populate_data(20);
    loop{
        main_screen(&TradesPeople);
    }
}