const dbConnection = {
  open: () => {},
  write: (data) => {},
  startTransaction: (operation) => {}
}

const logger = {
  log: console.log
}

function writeToDatabase (data) {
  dbConnection.open();
  // logger.log('hi ed')

  dbConnection.startTransaction(() => {
    // do a bunch of calculation
    // moar code
    changedData = data;
    db.write(changedData);
  });

  logger.log('success');
}

writeToDb({ 
  name: 'Dave', 
  generation: 'X' 
});




function writeToDatabase(data, onSuccess = () => {} ) {
 // open, spin a tx, write
 // if that goes well
 onSuccess();
}


writeToDatabase(userProfile, ()=> console.log("YAY");)
