export class LoggerService {
  public static debug(...msgs: any[]) {
    this.log('info', 'info', msgs);
  }

  public static success(...msgs: any[]) {
    this.log('info', 'success', msgs);
  }

  public static info(...msgs: any[]) {
    this.log('info', 'info', msgs);
  }

  public static error(...msgs: any[]) {
    this.log('error', 'error', msgs);
  }

  public static warn(...msgs: any[]) {
    this.log('warn', 'warn', msgs);
  }

  public static custom(
    msg: any,
    customStyle?: { textColor?: string; bgColor?: string },
  ) {
    if (typeof msg === 'string') {
      console.log(`%c${msg}`, this.style('info', customStyle));
    } else {
      console.log(msg);
    }
  }

  public static group(title: string, msgs: any[]) {
    console.group(title);
    this.info(msgs);
    console.groupEnd();
  }

  private static log(
    consoleMethod: 'info' | 'log' | 'warn' | 'error',
    styleType: 'info' | 'error' | 'warn' | 'success',
    msgs: any[],
  ) {
    console.group(consoleMethod, styleType, '------------------------------');
    msgs.forEach((msg: any) => {
      if (['string', 'number'].includes(typeof msg)) {
        console[consoleMethod](`%c${msg}`, this.style(styleType));
      } else {
        console.log(msg);
      }
    });
    console.log('---------------------------------------');
    console.groupEnd();
  }

  private static style(
    type: 'success' | 'error' | 'warn' | 'info',
    customStyle?: {
      textColor?: string;
      bgColor?: string;
    },
  ): string {
    let textColor = 'black';
    let bgColor = 'lightgray';

    if (!customStyle) {
      switch (type) {
        case 'success':
          {
            bgColor = 'green';
          }
          break;
        case 'warn':
          {
            bgColor = 'orange';
          }
          break;
        case 'error':
          {
            bgColor = 'red';
          }
          break;
      }
    } else {
      if (customStyle.bgColor) bgColor = customStyle.bgColor;
      if (customStyle.textColor) textColor = customStyle.textColor;
    }

    const text = `color: ${textColor}; font-size: 10px; `;
    const bg = `background-color: ${bgColor}; `;
    const container = 'padding: 5px 8px; border-radius: 10px; ';

    return text + bg + container;
  }
}
