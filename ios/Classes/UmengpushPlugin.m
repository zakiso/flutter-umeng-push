#import "UmengpushPlugin.h"
#import <umengpush/umengpush-Swift.h>

@implementation UmengpushPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftUmengpushPlugin registerWithRegistrar:registrar];
}
@end
