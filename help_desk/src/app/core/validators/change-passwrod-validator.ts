import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function changePasswordValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const password = control.value as string;

    if (!password || password=="") {
      return null;
    }

    const letterCount = (password.match(/[a-zA-Z]/g) || []).length;
    const digitCount = (password.match(/\d/g) || []).length; 

    if (letterCount >=5 && digitCount >= 2) {
      return null; 
    }

    return {
      passwordStrength: {
        valid: false,
        message: 'Minimalno 5 slova i 2 znamenke'
      }
    };
  };
}