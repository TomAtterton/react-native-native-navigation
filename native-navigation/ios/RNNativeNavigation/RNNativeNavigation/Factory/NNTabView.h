//
// Copyright (c) 2017 MediaMonks. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "NNView.h"

@class NNTabNode;

@interface NNTabView : UIViewController <NNView>

- (instancetype)initWithNode:(NNTabNode *)node;

@end
