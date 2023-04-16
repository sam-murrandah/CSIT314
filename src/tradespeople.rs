use rand::seq::SliceRandom;
use rand::Rng;

use crate::tradeprofessional::*;

const FIRST_NAMES: [&str; 17] = ["Adam", "Barry", "Craig", "Dale", "Erin", "Frank", "George", "Alice", "Betty", "Caroline", "Diana", "Emily", "Eirik", "Blanch", "Aldric", "Ffion", "Gillian"];
const LAST_NAMES: [&str; 17] = ["Anderson", "Baker", "Clark", "Davidson", "Evans", "Foster", "Gibson", "Harris", "Jones", "King", "Lee", "Moore", "Backus", "Tupper", "Augustin", "Lukeson", "Breckinridge"];
const PROFESSIONS: [&str; 8] = [
    "Tree Removal Specialist",
    "Roof Cleaning Technician",
    "Fence Installer",
    "Oven Repairman",
    "Plumbing Expert",
    "Electrical Contractor",
    "Landscape Designer",
    "Handyman",
];


pub struct Tradespeople {
    pub tradespeople: Vec<Tradeprofessional>,
}

impl Tradespeople {
    pub fn new() -> Tradespeople {
        Tradespeople {
            tradespeople: vec![],
        }
    }

    pub fn populate_data(&mut self, amount: u32) {
        for i in 1..=amount {
            let tradesperson = self.generate_random_tradesperson(i);
            self.tradespeople.push(tradesperson);
        }
    }

    pub fn get_tradesprofessional_by_id(&self, id: u32) -> Option<&Tradeprofessional> {
        for professional in &self.tradespeople {
            if professional.id == id {
                return Some(professional);
            }
        }
        None
    }
    

    pub fn generate_random_tradesperson(&self, id: u32) -> Tradeprofessional {
        let mut rng = rand::thread_rng();
    
        let random_first_name = String::from(*FIRST_NAMES.choose(&mut rng).unwrap());
        let random_last_name = String::from(*LAST_NAMES.choose(&mut rng).unwrap());
        let random_profession = String::from(*PROFESSIONS.choose(&mut rng).unwrap());
        let random_distance = rng.gen_range(1..=100);
        Tradeprofessional{
            id,
            first_name: random_first_name,
            last_name: random_last_name,
            profession: random_profession,
            distance: random_distance,
        }
    }
}
