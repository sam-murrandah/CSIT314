#[derive(Debug, Clone)]

pub struct Tradeprofessional {
    pub id: u32,
    pub first_name: String,
    pub last_name: String,
    pub profession: String,
    pub distance: u32
}

impl Tradeprofessional {
    pub fn to_string(&self) -> String {
        format!("{} {}, {}, {}kms (id: {})", self.first_name, self.last_name, self.profession, self.distance, self.id)
    }
}