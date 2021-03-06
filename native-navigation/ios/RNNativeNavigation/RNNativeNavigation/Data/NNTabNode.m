//
// Copyright (c) 2017 MediaMonks. All rights reserved.
//

#import "NNTabNode.h"
#import "NNTabView.h"
#import "NNNodeHelper.h"

static NSString *const kTabsKey = @"tabs";
static NSString *const kSelectedTabKey = @"selectedTab";

@interface NNTabNode ()

@property (nonatomic, strong) RCTBridge *bridge;
@property (nonatomic, copy) NSArray <id <NNNode>> *tabs;

@end

@implementation NNTabNode

+ (NSString *)jsName
{
    return @"TabView";
}

- (UIViewController <NNView> *)generate {
    return [[NNTabView alloc] initWithNode:self];
}

- (void)setData:(NSDictionary *)data
{
    [super setData:data];

    NSArray <NSDictionary *> *objects = data[kTabsKey];
    NSMutableArray <NNNode> *tempTabs = (NSMutableArray <NNNode> *)@[].mutableCopy;
    [objects enumerateObjectsUsingBlock:^(NSDictionary *obj, NSUInteger idx, BOOL *stop)
    {
        [tempTabs addObject:[NNNodeHelper.sharedInstance nodeFromMap:obj bridge:self.bridge]];
    }];
    self.tabs = tempTabs.copy;
    self.selectedTab = [data[kSelectedTabKey] unsignedIntegerValue];
}

- (NSDictionary *)data
{
    NSMutableDictionary *data = [super data].mutableCopy;
    NSMutableArray *tabs = @[].mutableCopy;
    [self.tabs enumerateObjectsUsingBlock:^(id <NNNode> obj, NSUInteger idx, BOOL *stop)
    {
        [tabs addObject:obj.data];
    }];
    data[kTabsKey] = tabs;
    data[kSelectedTabKey] = @(self.selectedTab);
	return data.copy;
}

- (NSString *)title
{
    return self.tabs.firstObject.title;
}

@end
