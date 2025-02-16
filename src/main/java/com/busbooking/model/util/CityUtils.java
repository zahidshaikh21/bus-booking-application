package com.busbooking.model.util;

import java.util.Arrays;
import java.util.List;

public class CityUtils { // Or put this in your existing service class

    public static List<String> getMaharashtraCities() {
        return Arrays.asList(
                "Mumbai", "Pune", "Nagpur", "Nashik", "Aurangabad", "Thane", "Navi Mumbai",
                "Solapur", "Amravati", "Kolhapur", "Sangli", "Akola", "Latur", "Jalgaon",
                "Bhiwandi", "Mira-Bhayandar", "Pimpri-Chinchwad", "Ahmednagar", "Dhule",
                "Ichalkaranji", "Parbhani", "Nanded", "Malegaon", "Amalner", "Pandharpur",
                "Achalpur", "Udgir", "Yavatmal", "Hingoli", "Nandurbar", "Osmanabad",
                "Buldhana", "Washim", "Ratnagiri", "Wardha", "Bhandara", "Gondia", "Chandrapur",
                "Gadchiroli", "Satara", "Karad", "Tasgaon", "Vita", "Kagal", "Barshi",
                "Lonavala", "Khandala", "Mahabaleshwar", "Matheran", "Pachgani", "Igatpuri",
                "Murtijapur", "Shegaon", "Daryapur", "Anjangaon Surji", "Achhnera", "Adilabad",
                "Afzalpur", "Agra", "Ahmedgarh", "Aizawl", "Ajmer", "Akbarpur", "Alappuzha",
                "Aligarh", "Allahabad", "Almora", "Alwar", "Ambala", "Ambikapur", "Ambur",
                "Amreli", "Amritsar", "Anand", "Anantapur", "Ara", "Araria", "Arrah", "Arsikere",
                "Asansol", "Aurangabad (Bihar)", "Azamgarh", "Badaun", "Bagaha", "Bahadurgarh",
                "Bahraich", "Balaghat", "Balangir", "Bali", "Ballia", "Balrampur", "Banda",
                "Bangalore", "Bankura", "Banswara", "Barabanki", "Bareilly", "Bargarh", "Baripada",
                "Barnala", "Barpeta", "Batala", "Bathinda", "Beawar", "Begusarai", "Belgaum",
                "Bellary", "Berhampore", "Bettiah", "Bhagalpur", "Bhandara", "Bharatpur", "Bharuch",
                "Bhavnagar", "Bhilai", "Bhilwara", "Bhimavaram", "Bhiwani", "Bhopal", "Bhubaneswar",
                "Bhuj", "Bidar", "Bihar Sharif", "Bijapur", "Bikaner", "Bilaspur", "Bina",
                "Bokaro Steel City", "Bongaigaon", "Botad", "Budaun", "Bulandshahr", "Burhanpur",
                "Buxar", "Chaibasa", "Chalakudy", "Chandausi", "Chandigarh", "Chapra", "Chattisgarh",
                "Chennai", "Cherthala", "Chhapra", "Chhindwara", "Chikmagalur", "Chittorgarh",
                "Chittur", "Coimbatore", "Cooch Behar", "Cuddalore", "Cuttack", "Dahod", "Daltonganj",
                "Daman & Diu", "Darbhanga", "Datia", "Davangere", "Dehradun", "Delhi", "Deoghar",
                "Dera Bassi", "Dewas", "Dhanbad", "Dhar", "Dharmapuri", "Dholpur", "Dibrugarh",
                "Dimapur", "Dindigul", "Dispur", "Diu", "Dogra", "Dumka", "Durg", "Durgapur",
                "Dwarka", "Eluru", "Erode", "Etah", "Etawah", "Faizabad", "Faridabad", "Farrukhabad",
                "Fatehgarh", "Fatehpur", "Fazilka", "Firozabad", "Firozpur", "Gadwal", "Gandhidham",
                "Gandhinagar", "Gaya", "Ghaziabad", "Ghazipur", "Giridih", "Goa", "Godhra", "Golaghat",
                "Gonda", "Gorakhpur", "Gulbarga", "Gumla", "Guna", "Guntur", "Gurdaspur", "Gurgaon",
                "Guwahati", "Gwalior", "Habra", "Hajipur", "Haldia", "Haldwani", "Hamirpur", "Hansi",
                "Hanumangarh", "Haora", "Hardoi", "Haridwar", "Haryana", "Hassan", "Hathras",
                "Haveri", "Hazaribagh", "Himachal Pradesh", "Himatnagar", "Hissar", "Hoshangabad",
                "Hosur", "Hubli", "Hyderabad", "Idukki", "Imphal", "Indore", "Itanagar", "Jabalpur",
                "Jagdalpur", "Jaipur", "Jalandhar", "Jalna", "Jammu", "Jamnagar", "Jamshedpur",
                "Janjgir-Champa", "Jaora", "Jashpur", "Jatani", "Jaurasi", "Jehanabad", "Jeypore",
                "Jhansi", "Jharsuguda", "Jhajjar", "Jhalawar", "Jhalda", "Jind", "Jodhpur", "Jorhat",
                "Junagadh", "Kadapa", "Kakinada", "Kalyan-Dombivli", "Kamakhyanagar", "Kamptee",
                "Kanchipuram", "Kandla", "Kangra", "Kanhangad", "Kannauj", "Kanpur", "Kanyakumari",
                "Kapurthala", "Karaikal", "Karaikudi", "Kargil", "Karimganj", "Karimnagar", "Karnal",
                "Kasargod", "Kashipur", "Katihar", "Katni", "Katra", "Kavaratti", "Kayamkulam",
                "Kendujhar", "Keonjhar", "Kerala", "Keshod", "Khagaria", "Khajuraho", "Khamgaon",
                "Khandwa", "Khanna", "Kharagpur", "Kharar", "Kharsia", "Khatra", "Kheda", "Khejuri",
                "Khunti", "Khurda", "Kichha", "Kochi", "Kodagu", "Kodungallur", "Kohima", "Kolar",
                "Kolkata", "Kollam", "Kopargaon", "Koraput", "Kota", "Kotdwara", "Kottayam",
                "Kozhikode", "Krishnanagar", "Kulgam", "Kullu", "Kumbakonam", "Kurnool", "Kurukshetra",
                "Ladakh", "Lakhimpur", "Lalitpur", "Latur", "Leh", "Lucknow", "Ludhiana", "Lunglei",
                "Madhepura", "Madhu Bani", "Madikeri", "Madurai", "Mahad", "Maharajganj", "Maharashtra",
                "Mahbubnagar", "Mahesana", "Mainpuri", "Mairang", "Majuli", "Makar Sankranti",
                "Malappuram", "Malkangiri", "Mallapuram", "Manali", "Mancherial", "Mandsaur",
                "Mandya", "Mangaldoi", "Mangalore", "Manipal", "Manipala", "Manjeri", "Mankachar",
                "Margao", "Margherita", "Mathura", "Mattannur", "Mavelikara", "Meerut", "Mehsana",
                "Merta City", "Mewat", "Mirzapur", "Mohali", "Mokokchung", "Mon", "Moradabad",
                "Morena", "Motihari", "Mughalsarai", "Mukerian", "Munger", "Muntazabad", "Murshidabad",
                "Murtijapur", "Muzaffarnagar", "Muzaffarpur", "Mysore", "Nabadwip", "Nabha", "Nadad",
                "Nadia", "Nagaon", "Nagapattinam", "Nagercoil", "Nagpur", "Nalgonda", "Naliya",
                "Nalanda", "Nanded", "Nandurbar", "Nandyal", "Nangal", ""
        );


    }
}