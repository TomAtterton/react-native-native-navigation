//
// Copyright (c) 2017 MediaMonks. All rights reserved.
//

#import "NNNode.h"
#import "NNBaseNode.h"

@interface NNStackNode : NNBaseNode <NNNode>

@property (nonatomic, copy) NSArray <id <NNNode>> *stack;

@end
