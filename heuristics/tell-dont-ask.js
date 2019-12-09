class Monitor {

  constructor(limit = 10) {
    this.limit = 10;
  }

  setValue(theValue) {
    this.value = theValue;
  }

  getValue() {
    return this.value;
  }

  evalAlarm(action) {
    if (this.getValue() > this.getLimit()) {
      action();
    } 
  }

}

// consumer code
const mon1 = new Monitor(50);

mon1.setValue(51);

mon1.evalAlarm(() => console.log('ah-oo-ga'));
