#!/bin/bash

CWD="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"
PARENT="$(cd $CWD/.. >/dev/null 2>&1 && pwd)"

echo "Initializing script..."
echo "CWD: $CWD"
echo "ROOT: $PARENT"

function showUsage() {
  echo -e "Usage:\n\t./secrets <encrypt|decrypt> [opts]"
  echo -e "\nExamples"
  echo -e "\nEncrypt:\n\t./secrets encrypt"
  echo -e "\nDecrypt: \n\t./secrets decrypt [password]"
  echo -e "\tThe [password] parameter is required for CI environments, but if it is\n\tempty the user will be prompted to enter the password."
}

# Args:
# $1 - e|d - Encrypt or decrypt
# $2 - password
# $3 - in
# $4 - out
function crypto() {
  openssl aes-256-cbc "-$1" -k "$2" -in "$3" -out "$4" -md sha256
  retVal=$?
  if [ $retVal -ne 0 ]; then
    # rm $2
    echo "Unable to process $3"
    echo "Exiting..."
    exit 1
  fi

  echo "Processed: $3 -> $4"
}

function decrypt() {
  echo "Running in decrypt mode."

  password=$2
  if [ -z "$password" ]; then
    if [ "$CI" = true ]; then
      echo "Detected a CI environment, but no password was supplied!"
      showUsage
      exit 1
    else
      read -sp "Enter the password: " password
      if [ -z "$password" ]; then
        echo "No password was supplied!"
        exit 1
      fi
    fi
  fi

  echo -e "\nDecrypting keystore..."
  crypto "d" "$password" "$CWD/encrypted-whalesay.keystore" "$PARENT/whalesay.keystore"

  echo "Decrypting secrets.properties..."
  crypto "d" "$password" "$CWD/encrypted-secrets.properties" "$PARENT/secrets.properties"

  echo "Decrypting private_key.pepk..."
  crypto "d" "$password" "$CWD/encrypted-private_key.pepk" "$PARENT/private_key.pepk"

  echo "Decrypting google-services.json..."
  crypto "d" "$password" "$CWD/encrypted-google-services.json" "$PARENT/mobile/google-services.json"
}

function encrypt() {
  echo "Running in encrypt mode."

  read -sp "Enter the password: " password
  echo
  read -sp "Confirm password: " confirm

  if [ "$password" != "$confirm" ]; then
    echo -ne "\nThe entered passwords do not match!"
    exit 1
  fi

  echo -e "\nEncrypting keystore..."
  crypto "e" "$password" "$PARENT/whalesay.keystore" "$CWD/encrypted-whalesay.keystore"

  echo "Encrypting secrets.properties..."
  crypto "e" "$password" "$PARENT/secrets.properties" "$CWD/encrypted-secrets.properties"

  echo "Encrypting private_key.pepk..."
  crypto "e" "$password" "$PARENT/private_key.pepk" "$CWD/encrypted-private_key.pepk"

  echo "Encrypting google-services.json..."
  crypto "e" "$password" "$PARENT/mobile/google-services.json" "$CWD/encrypted-google-services.json"
}

if [[ "$1" == "encrypt" ]]; then
  encrypt "$@"
elif [[ "$1" == "decrypt" ]]; then
  decrypt "$@"
else
  showUsage
  exit 1
fi

echo "Have a nice day!"
