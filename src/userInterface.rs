use crate::tradespeople::*;
use std::io;
use std::process;

pub fn main_screen(tradespeople: &Tradespeople) {
    line();
    println!(" 1) Print all Trades People");
    println!(" 2) Print specific Trade person by ID");
    println!(" 0) Exit");
    line();
    println!("\n Enter selection:");
    let mut selection = String::new();
    io::stdin().read_line(&mut selection).expect("Failed to read line");

    match selection.trim().parse::<u32>() {
        Ok(id) => {
            match id {
                0 => process::exit(0),
                1 => print_professionals(tradespeople),
                2 => get_tradesprofessional(tradespeople),
                _ => println!("Invalid selection."),
            }
        }
        Err(error) => println!("This is not a number: {}", error),
    }
    println!("\n\n\n");
}

pub fn get_tradesprofessional(tradespeople: &Tradespeople) {
    println!("Enter professional ID:");
    let mut input = String::new();
    io::stdin().read_line(&mut input).expect("Failed to read line");
    match input.trim().parse::<u32>() {
        Ok(id) => match tradespeople.get_tradesprofessional_by_id(id) {
            Some(professional) => println!("{:?}", professional.to_string()),
            None => println!("No trades professional with id {}", id),
        },
        Err(error) => println!("This is not a number: {}", error),
    }
}

pub fn print_professionals(tradespeople: &Tradespeople) {
    for person in &tradespeople.tradespeople {
        println!("  {}", person.to_string());
    }
}

pub fn line() {
    println!("-----------------------------------");
}
