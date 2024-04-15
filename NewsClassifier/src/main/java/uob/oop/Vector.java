package uob.oop;

public class Vector {
    private double[] doubElements;

    public Vector(double[] _elements) {
        //TODO Task 3.1 - 0.5 marks
        doubElements = _elements;
    }

    public double getElementatIndex(int _index) {
        //TODO Task 3.2 - 2 marks
        if (_index >= doubElements.length){
            return -1;
        }
        else {
            return doubElements[_index]; //you need to modify the return value
        }

    }

    public void setElementatIndex(double _value, int _index) {
        //TODO Task 3.3 - 2 marks
        if(_index>= doubElements.length){
            doubElements[doubElements.length-1] = _value;
        }
        else{
            doubElements[_index] = _value;
        }
    }

    public double[] getAllElements() {
        //TODO Task 3.4 - 0.5 marks

        return doubElements; //you need to modify the return value
    }

    public int getVectorSize() {
        //TODO Task 3.5 - 0.5 marks
        return doubElements.length; //you need to modify the return value
    }

    public Vector reSize(int _size) {
        //TODO Task 3.6 - 6 marks
        if (doubElements.length == _size || _size<=0){
            Vector oldVector = new Vector(doubElements);
            return oldVector;
        }
        else{
            if (_size < doubElements.length){
                double[] newElements = new double[_size];
                for(int i = 0; i<newElements.length;i++){
                    newElements[i] = doubElements[i];
                }
                Vector newVector = new Vector(newElements);
                return newVector;
            }
            else{
                double[] newElements = new double[_size];
                for (int i =0;i<doubElements.length;i++){
                    newElements[i] = doubElements[i];
                }
                for (int i = doubElements.length;i<newElements.length;i++){
                    newElements[i] = -1;
                }
                Vector newVector = new Vector(newElements);
                return newVector;
            }
        }

    }

    public Vector add(Vector _v) {
        //TODO Task 3.7 - 2 marks
        Vector originalVector = new Vector(doubElements);
        if (_v.getVectorSize()>doubElements.length){
            originalVector = originalVector.reSize(_v.getVectorSize());
        }
        else {
            _v = _v.reSize(originalVector.getVectorSize());
        }
        double[] addition = new double[_v.getVectorSize()];
        for (int i = 0;i<addition.length;i++){
            addition[i] = _v.getAllElements()[i] + originalVector.getAllElements()[i];
        }
        Vector result = new Vector(addition);
        return result; //you need to modify the return value
    }

    public Vector subtraction(Vector _v) {
        //TODO Task 3.8 - 2 marks
        Vector originalVector = new Vector(doubElements);
        if (_v.getVectorSize()>doubElements.length){
            originalVector = originalVector.reSize(_v.getVectorSize());
        }
        else {
            _v = _v.reSize(originalVector.getVectorSize());
        }
        double[] subtraction = new double[_v.getVectorSize()];
        for (int i = 0;i<subtraction.length;i++){
            subtraction[i] = originalVector.getAllElements()[i] - _v.getAllElements()[i];
        }
        Vector result = new Vector(subtraction);
        return result; //you need to modify the return value
    }

    public double dotProduct(Vector _v) {
        //TODO Task 3.9 - 2 marks
        Vector originalVector = new Vector(doubElements);
        if (_v.getVectorSize()>doubElements.length){
            originalVector = originalVector.reSize(_v.getVectorSize());
        }
        else {
            _v = _v.reSize(originalVector.getVectorSize());
        }
        double dotProduct = 0;
        for (int i = 0;i< _v.getVectorSize();i++){
            dotProduct += (_v.getElementatIndex(i) * originalVector.getElementatIndex(i));
        }

        return dotProduct; //you need to modify the return value
    }

    public double cosineSimilarity(Vector _v) {
        //TODO Task 3.10 - 6.5 marks
        Vector originalVector = new Vector(doubElements);
        if (_v.getVectorSize()>doubElements.length){
            originalVector = originalVector.reSize(_v.getVectorSize());
        }
        else {
            _v = _v.reSize(originalVector.getVectorSize());
        }
        double csNumerator = dotProduct(_v);
        double squareOfOriginalVectorElements = 0;
        double squareOf_VVectorElements = 0;
        for (int i = 0; i<_v.getVectorSize(); i++){
            squareOfOriginalVectorElements += Math.pow(originalVector.getElementatIndex(i),2);
            squareOf_VVectorElements += Math.pow(_v.getElementatIndex(i),2);
        }
        double csDenminator = Math.sqrt(squareOf_VVectorElements) * Math.sqrt(squareOfOriginalVectorElements);
        double csSimilarity = csNumerator/csDenminator;

        return csSimilarity; //you need to modify the return value
    }

    @Override
    public boolean equals(Object _obj) {
        Vector v = (Vector) _obj;
        boolean boolEquals = true;

        if (this.getVectorSize() != v.getVectorSize())
            return false;

        for (int i = 0; i < this.getVectorSize(); i++) {
            if (this.getElementatIndex(i) != v.getElementatIndex(i)) {
                boolEquals = false;
                break;
            }
        }
        return boolEquals;
    }

    @Override
    public String toString() {
        StringBuilder mySB = new StringBuilder();
        for (int i = 0; i < this.getVectorSize(); i++) {
            mySB.append(String.format("%.5f", doubElements[i])).append(",");
        }
        mySB.delete(mySB.length() - 1, mySB.length());
        return mySB.toString();
    }
}
