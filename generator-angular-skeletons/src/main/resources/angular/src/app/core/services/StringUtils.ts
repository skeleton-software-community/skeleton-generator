export class StringUtils {

  static emptyToNull(arg:any) {
    return arg == ''?null:arg;
  }

  static stringToStrictBoolean(arg:any) {
    if (arg == '' || arg == null) {return false;}

    return arg;
  }

  static stringToNullableBoolean(arg:any) {
    if (arg == false) {return false;}
    if (arg == true) {return true;}

    return null;
  }
}