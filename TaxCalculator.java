// interface of the TaxCalculator
interface TaxCalculator {
    double calculateTax(double grossIncome, double taxCredits);
}

// Class implements the interface and override the CalculateTax function
class TaxCalculatorImpl implements TaxCalculator {
    @Override
    public double calculateTax(double grossIncome, double taxCredits) {
        return grossIncome * 0.2 - taxCredits;
    }
}


